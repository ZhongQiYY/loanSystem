package com.zhong.loan.pojo;

import java.time.LocalDate;
import java.util.Date;

/**
 * 还本方式
 */
public class Repay {
    private LocalDate repayDate; //还本日期
    private double repayment; //还本金额

    public LocalDate getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(LocalDate repayDate) {
        this.repayDate = repayDate;
    }

    public double getRepayment() {
        return repayment;
    }

    public void setRepayment(double repayment) {
        this.repayment = repayment;
    }

    @Override
    public String toString() {
        return "Repay{" +
                "repayDate=" + repayDate +
                ", repayment=" + repayment +
                '}';
    }
}
