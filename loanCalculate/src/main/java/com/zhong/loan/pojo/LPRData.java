package com.zhong.loan.pojo;

import java.time.LocalDate;

public class LPRData {
    private LocalDate lprDate;
    private double lprRate;

    public LocalDate getLprDate() {
        return lprDate;
    }

    public void setLprDate(LocalDate lprDate) {
        this.lprDate = lprDate;
    }

    public double getLprRate() {
        return lprRate;
    }

    public void setLprRate(double lprRate) {
        this.lprRate = lprRate;
    }
}
