package com.zhong.loan.pojo;

import java.time.LocalDate;
import java.util.List;

/**
 * 利息
 */
public class Interest {
    private LocalDate interestDate; //结息日期
    private double interest; //利息金额
    private List<LPRData> lprData;//浮动利率信息

    public LocalDate getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(LocalDate interestDate) {
        this.interestDate = interestDate;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public List<LPRData> getLprData() {
        return lprData;
    }

    public void setLprData(List<LPRData> lprData) {
        this.lprData = lprData;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "interestDate=" + interestDate +
                ", interest=" + interest +
                ", lprData=" + lprData +
                '}';
    }
}
