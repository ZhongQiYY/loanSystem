package com.zhong.loan.utils;

import com.zhong.loan.pojo.LPRData;
import com.zhong.loan.pojo.Loan;
import com.zhong.loan.pojo.Repay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class LoanUtils {

    /**
     * 根据list中存储的Repay对象的repayDate字段进行排序
     *
     * @param repays Repay对象集合
     * @return Repay对象集合
     */
    public static List<Repay> sortRepay(List<Repay> repays) {
        repays.sort(new Comparator<Repay>() {
            @Override
            public int compare(Repay o1, Repay o2) {
                if (o1.getRepayDate().isAfter(o2.getRepayDate())) return 1;
                if (o1.getRepayDate().isEqual(o2.getRepayDate())) return 0;
                return -1;
            }
        });
        return repays;
    }

    /**
     * 获取还款日集合
     * @param startDate 贷款开始时间
     * @param endDate 结束时间
     * @param style 结息方式
     * @return 还款日集合
     */
    public static List<LocalDate> getDueDate(LocalDate startDate, LocalDate endDate, String style) {
        List<LocalDate> localDates = new ArrayList<>();
        localDates.add(startDate);
        long length = ChronoUnit.DAYS.between(startDate, endDate);
        if (style.equals("month")) {
            for (long i = length - 1; i > 0; i--) { //length-1不取第一天的日期，i>0不取最后一天的日期
//            获取每月的20号存入集合
                if (endDate.minusDays(i).getDayOfMonth() == 20) localDates.add(endDate.minusDays(i));
            }
        }
        if(style.equals("season")){
            for (long i = length - 1; i > 0; i--) {
                int month = endDate.minusDays(i).getMonthValue();
                if (month == 3 || month == 6 || month == 9 || month == 12) {
                    if (endDate.minusDays(i).getDayOfMonth() == 20) localDates.add(endDate.minusDays(i));
                }
            }
        }
        if(style.equals("year")){
            for (long i = length - 1; i > 0; i--) {
//            获取每1月的20号存入集合
                if (endDate.minusDays(i).getMonthValue() == 1 && endDate.minusDays(i).getDayOfMonth() == 20)
                    localDates.add(endDate.minusDays(i));
            }
        }
        localDates.add(endDate);
        return localDates;
    }

    /**
     * 每月LPR变化一次
     * @param startDate 贷款开始时间
     * @param endDate 结束时间
     */
    public static List<LPRData> getDueDateLPR(LocalDate startDate, LocalDate endDate, int dayOfLPR) {
        List<LPRData> localDates = new ArrayList<>();
        LPRData lprData1 = new LPRData();
        lprData1.setLprDate(startDate);
        lprData1.setLprRate(getRandom());
        localDates.add(lprData1);
        long length = ChronoUnit.DAYS.between(startDate, endDate);
        for (long i = length - 1; i > 0; i--) { //length-1不取第一天的日期，i>0不取最后一天的日期
            //获取每月的20号存入集合
            if (endDate.minusDays(i).getDayOfMonth() == dayOfLPR){
                LPRData lprData = new LPRData();
                lprData.setLprDate(endDate.minusDays(i));
                lprData.setLprRate(getRandom());
                localDates.add(lprData);
            }
        }
        LPRData lprData2 = new LPRData();
        lprData2.setLprDate(endDate);
        lprData2.setLprRate(getRandom());
        localDates.add(lprData2);
        return localDates;
    }

    /**
     * 使用随机数模拟生成LRP利率
     * @return LRP利率
     */
    public static double getRandom(){
        Random random = new Random();//用来生成LRP利率
//        只保留两位小数
        return new BigDecimal(random.nextInt(8) + random.nextDouble())
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * 判断目标日期是否在两个日期之间，只包含后相等情况
     * 即 start < targetDate <= edn
     *
     * @param start      开始日期
     * @param end        结束日期
     * @param targetDate 目标日期
     * @return true/false
     */
    public static boolean betweenAndAfterEqual(LocalDate start, LocalDate end, LocalDate targetDate) {
        return targetDate.isAfter(start)
                &&
                (targetDate.isBefore(end) || targetDate.isEqual(end));
    }


    /**
     * 验证数据是否合法
     *
     * @param loan 贷款数据
     * @return 不合法：0   合法：1
     */
    public static int isLegal(Loan loan) {
        if (loan.getStartDate() == null
                || loan.getEndDate() == null
                || loan.getLoanMoney() <= 0
                || loan.getLoanRate() == 0) {
            return 0;
        }

        if(loan.getInterestAccrual().equals("floatingRate") && loan.getDayLPR() == 0) return 0;
//        if(loan.getRepays().size() > 0){
//            for (Repay repay : loan.getRepays()) {
//                if(repay)
//            }
//        }
        LocalDate startDate = loan.getStartDate();//贷款开始时间
        LocalDate endDate = loan.getEndDate();//贷款结束时间
        if (startDate.isEqual(endDate) || startDate.isAfter(endDate)) return 0;
        return 1;
    }
}
