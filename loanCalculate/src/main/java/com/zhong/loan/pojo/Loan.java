package com.zhong.loan.pojo;

import java.time.LocalDate;
import java.util.List;

/**
 * 贷款信息
 */
public class Loan {
    private double loanMoney; //贷款金额
    private LocalDate startDate; //贷款开始时间
    private LocalDate endDate; //贷款结束时间
    private double loanRate; //年利率
    private String interestAccrual;//计息方式
    private String insterestStyle;//结息方式
    private List<Repay> repays; //还本方式
    private int dayLPR;//每月LPR变化的那一天

    public double getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(double loanMoney) {
        this.loanMoney = loanMoney;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(double loanRate) {
        this.loanRate = loanRate;
    }

    public List<Repay> getRepays() {
        return repays;
    }

    public void setRepays(List<Repay> repays) {
        this.repays = repays;
    }

    public String getInsterestStyle() {
        return insterestStyle;
    }

    public void setInsterestStyle(String insterestStyle) {
        this.insterestStyle = insterestStyle;
    }

    public String getInterestAccrual() {
        return interestAccrual;
    }

    public void setInterestAccrual(String interestAccrual) {
        this.interestAccrual = interestAccrual;
    }

    public int getDayLPR() {
        return dayLPR;
    }

    public void setDayLPR(int dayLPR) {
        this.dayLPR = dayLPR;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanMoney=" + loanMoney +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", loanRate=" + loanRate +
                ", interestAccrual='" + interestAccrual + '\'' +
                ", insterestStyle='" + insterestStyle + '\'' +
                ", repays=" + repays +
                ", dayLPR=" + dayLPR +
                '}';
    }
}
