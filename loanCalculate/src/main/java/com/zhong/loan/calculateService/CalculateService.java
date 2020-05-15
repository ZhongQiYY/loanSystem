package com.zhong.loan.calculateService;

import com.zhong.loan.pojo.Interest;
import com.zhong.loan.pojo.LPRData;
import com.zhong.loan.pojo.Loan;
import com.zhong.loan.pojo.Repay;
import com.zhong.loan.utils.LoanUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CalculateService {

    private double loanMoney;//贷款本金
    private double loanRate;//贷款年利率
    private List<Interest> interests = new ArrayList<>();//利息

    /**
     * 固定利率计算利息
     * @param loan 贷款信息
     * @param localDates 还款日期集合
     * @return 利息集合
     */
    public List<Interest> calculate(Loan loan, List<LocalDate> localDates) {
        List<Repay> repays = LoanUtils.sortRepay(loan.getRepays());//根据还本计划内的日期进行排序
        LocalDate startDate = loan.getStartDate();//贷款开始时间

        //如果在贷款当天还了一笔款，那么将贷款金额减少
        for (Repay repay : repays) {
            if(repay.getRepayDate().isEqual(startDate))
                loanMoney = loanMoney - repay.getRepayment();
        }

        for (int i = 0; i < localDates.size() - 1; i++) {
            //start到end为一段还款周期
            LocalDate start = localDates.get(i);
            LocalDate end = localDates.get(i + 1);//end为一个周期的还息日
            //获取这一段还款周期的利息
            Interest interest = new Interest();//创建一个利息对象，用来存放一段利息数据
            interest.setInterest(new BigDecimal(getInterest(start, end, repays))
                                .setScale(2, BigDecimal.ROUND_HALF_UP)
                                .doubleValue());
            interest.setInterestDate(end);
            interests.add(interest);
        }
        List<Interest> ins = interests;
        interests = new ArrayList<>();

        return ins;
    }

    /**
     * LPR计算利息
     * @param loan 贷款信息
     * @param localDates 还款日集合
     * @param lprData lpr数据信息
     */
    public List<Interest> calculateLPR(Loan loan, List<LocalDate> localDates, List<LPRData> lprData) {
        List<Repay> repays = LoanUtils.sortRepay(loan.getRepays());//根据还本计划内的日期进行排序
        LocalDate startDate = loan.getStartDate();//贷款开始时间

        //如果在贷款当天还了一笔款，那么将贷款金额减少
        for (Repay repay : repays) {
            if(repay.getRepayDate().isEqual(startDate))
                loanMoney = loanMoney - repay.getRepayment();
        }

        for (int i = 0; i < localDates.size() - 1; i++) {
            //start到end为一段还款周期
            LocalDate start = localDates.get(i);
            LocalDate end = localDates.get(i + 1);//end为一个周期的还息日
            //获取这一段还款周期的利息
            Interest interest = new Interest();//创建一个利息对象，用来存放一段利息数据
            interest.setInterest(new BigDecimal(getInterestLPR(start, end, repays, lprData, loan))
                    .setScale(2, BigDecimal.ROUND_HALF_UP)
                    .doubleValue());
            interest.setInterestDate(end);
            interests.add(interest);
        }
        List<Interest> ins = interests;
        if(ins.size() > 0) ins.get(ins.size() - 1).setLprData(lprData);//将LPR一并发送至前端
        interests = new ArrayList<>();

        return ins;
    }

    /**
     * 月结息
     */
    public List<Interest> month(Loan loan) {
        loanMoney = loan.getLoanMoney();
        loanRate = loan.getLoanRate();
        if(loan.getInterestAccrual().equals("fixedRate")){
            return calculate(loan, LoanUtils.getDueDate(loan.getStartDate(), loan.getEndDate(), "month"));
        }else if(loan.getInterestAccrual().equals("floatingRate")){
//            获取LPR变化的数据，包括日期和利率
            List<LPRData> dueDateLPR = LoanUtils.getDueDateLPR(loan.getStartDate(), loan.getEndDate(), loan.getDayLPR());
            return calculateLPR(loan, LoanUtils.getDueDate(loan.getStartDate(), loan.getEndDate(), "month"), dueDateLPR);
        }
        return null;
    }

    /**
     * 季结息
     */
    public List<Interest> season(Loan loan) {
        loanMoney = loan.getLoanMoney();
        loanRate = loan.getLoanRate();
        if(loan.getInterestAccrual().equals("fixedRate")){
            return calculate(loan, LoanUtils.getDueDate(loan.getStartDate(), loan.getEndDate(), "season"));
        }else if(loan.getInterestAccrual().equals("floatingRate")){
            List<LPRData> dueDateLPR = LoanUtils.getDueDateLPR(loan.getStartDate(), loan.getEndDate(), loan.getDayLPR());
            return calculateLPR(loan, LoanUtils.getDueDate(loan.getStartDate(), loan.getEndDate(), "season"), dueDateLPR);
        }
        return null;
    }

    /**
     * 年结息
     */
    public List<Interest> year(Loan loan) {
        loanMoney = loan.getLoanMoney();
        loanRate = loan.getLoanRate();
        if(loan.getInterestAccrual().equals("fixedRate")){
            return calculate(loan, LoanUtils.getDueDate(loan.getStartDate(), loan.getEndDate(), "year"));
        }else if(loan.getInterestAccrual().equals("floatingRate")){
            List<LPRData> dueDateLPR = LoanUtils.getDueDateLPR(loan.getStartDate(), loan.getEndDate(), loan.getDayLPR());
            return calculateLPR(loan, LoanUtils.getDueDate(loan.getStartDate(), loan.getEndDate(), "year"), dueDateLPR);
        }
        return null;
    }

    /**
     * 用来获取一段周期的利息
     * @param start 开始时间
     * @param end 还息日
     * @param repays 还本计划
     * @return 利息
     */
    public double getInterest(LocalDate start, LocalDate end, List<Repay> repays){
        double interest = 0;
        //遍历还本方式，如果有还本日期在这段还款日之间，先计算到还本日期时间段内的利息
        for (Repay repay : repays) {
//            若在这段时间内有还本
            if(LoanUtils.betweenAndAfterEqual(start, end, repay.getRepayDate()) && loanMoney > 0){
//                利息计算开始时间到还本时间的天数
                long days = ChronoUnit.DAYS.between(start, repay.getRepayDate());
//                计算这段时间的利息
                interest = interest + (loanMoney*loanRate*days)/36000;
//                将总金额减去还的本金
                loanMoney = loanMoney - repay.getRepayment();
//                利息计算开始时间前移
                start = repay.getRepayDate();
            }
        }
        if(loanMoney > 0){
//            中间没有还本了，计算此段时间内的利息
            long days = ChronoUnit.DAYS.between(start, end);
            interest = interest + (loanMoney*loanRate*days)/36000;
        }
        return interest;
    }

    /**
     * LPR，用来获取一段周期的利息
     * @param start 开始时间
     * @param end 结束时间
     * @param repays 还本计划
     * @param lprData LPR利率与变化日期
     * @param loan 贷款信息
     * @return 利息
     */
    public double getInterestLPR(LocalDate start, LocalDate end, List<Repay> repays, List<LPRData> lprData, Loan loan){
        double interest = 0;
        loanRate = loanRate + lprData.get(0).getLprRate();//1.设置初始的利率
        for (Repay repay : repays) {
            if(LoanUtils.betweenAndAfterEqual(start, end, repay.getRepayDate()) && loanMoney > 0){
                //2.在还本计划段内，到调利率之前的利息算清
                for (LPRData lprDatum : lprData) {
                    LocalDate date = lprDatum.getLprDate();
//                    若在利息计算开始时间到还本时间之间有LPR变化，那么先计算开始时间到变化时间的利息
                    if(LoanUtils.betweenAndAfterEqual(start, repay.getRepayDate(), date)){
                        long days = ChronoUnit.DAYS.between(start, date);
                        interest = interest + (loanMoney*loanRate*days)/36000;
//                        计算开始时间前移
                        start = date;
//                        利率变化
                        loanRate = lprDatum.getLprRate() + loan.getLoanRate();
                    }
                }
                long days = ChronoUnit.DAYS.between(start, repay.getRepayDate());
                interest = interest + (loanMoney*loanRate*days)/36000;
                loanMoney = loanMoney - repay.getRepayment();
                start = repay.getRepayDate();
            }
        }
        //3.在还本计划段外，到调利率之前的利息算清
        for (LPRData lprDatum : lprData) {
            LocalDate date = lprDatum.getLprDate();
//            在最后这段时间内如果有利率变化，做同上操作
            if(LoanUtils.betweenAndAfterEqual(start, end, date)){
                long days = ChronoUnit.DAYS.between(start, date);
                interest = interest + (loanMoney*loanRate*days)/36000;
                start = date;
                loanRate = lprDatum.getLprRate() + loan.getLoanRate();
            }
        }
        if(loanMoney > 0){
            long days = ChronoUnit.DAYS.between(start, end);
            interest = interest + (loanMoney*loanRate*days)/36000;
        }
        return interest;
    }

}
