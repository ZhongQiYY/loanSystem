package com.zhong.loan.utils;


import com.zhong.loan.pojo.Loan;
import com.zhong.loan.pojo.Repay;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 弃用的工具类
 */
public class LoanUtils_Deprecated {

//    public static List<Repay> sortRepay(List<Repay> repays){
//        repays.sort(new Comparator<Repay>() {
//            @Override
//            public int compare(Repay o1, Repay o2) {
//                if (o1.getRepayDate().isAfter(o2.getRepayDate())) return 1;
//                if (o1.getRepayDate().isEqual(o2.getRepayDate())) return 0;
//                return -1;
//            }
//        });
//        return repays;
//    }


//    public static List<LocalDate> getSeason(LocalDate startDate, LocalDate endDate){
//        List<LocalDate> seasonDates = new ArrayList<>();
//        seasonDates.add(startDate);
//        long length = ChronoUnit.DAYS.between(startDate, endDate);
//        for (long i = length - 1; i > 0; i--) { //length-1不取第一天的日期，i>0不取最后一天的日期
//            int month = endDate.minusDays(i).getMonthValue();
//            if(month == 3 || month == 6 || month == 9 || month == 12){
//                if(endDate.minusDays(i).getDayOfMonth() == 20) seasonDates.add(endDate.minusDays(i));
//            }
//        }
//        seasonDates.add(endDate);
//        return seasonDates;
//    }


//    public static List<LocalDate> getMonth(LocalDate startDate, LocalDate endDate) {
//        List<LocalDate> monthDates = new ArrayList<>();
//        monthDates.add(startDate);
//        long length = ChronoUnit.DAYS.between(startDate, endDate);
//        for (long i = length - 1; i > 0; i--) { //length-1不取第一天的日期，i>0不取最后一天的日期
////            获取每月的20号存入集合
//            if(endDate.minusDays(i).getDayOfMonth() == 20) monthDates.add(endDate.minusDays(i));
//        }
//        monthDates.add(endDate);
//        return monthDates;
//    }


//    public static List<LocalDate> getYear(LocalDate startDate, LocalDate endDate) {
//        List<LocalDate> yearDates = new ArrayList<>();
//        Period period = Period.between(startDate, endDate);//这个方法可以得到两个日期相差的年月日
//        yearDates.add(startDate);
////        int years = period.getYears();
////        if(years >= 1){
////            LocalDate ld = startDate;
////            for (int i = 0; i < years; i++) {
////                ld = ld.plusYears(1);
////                yearDates.add(ld);//增加一年
////            }
////            //如果结束日期刚好是一年的周期，那么直接返回
////            if(ld.isEqual(endDate)) return yearDates;
////        }
//        long length = ChronoUnit.DAYS.between(startDate, endDate);
//        for (long i = length - 1; i > 0; i--) { //length-1不取第一天的日期，i>0不取最后一天的日期
////            获取每月的20号存入集合
//            if(endDate.minusDays(i).getMonthValue() == 1 && endDate.minusDays(i).getDayOfMonth() == 20)
//                yearDates.add(endDate.minusDays(i));
//        }
//        yearDates.add(endDate);
//        return yearDates;
//    }


//    public static boolean betweenAndAfterEqual(LocalDate start, LocalDate end, LocalDate targetDate){
//        return targetDate.isAfter(start)
//                &&
//                (targetDate.isBefore(end) || targetDate.isEqual(end));
//    }
//
//
//
//    public static int isLegal(Loan loan){
//        if(loan.getStartDate() == null
//                || loan.getEndDate() == null
//                || loan.getLoanMoney() <= 0
//                || loan.getLoanRate() == 0){
//            return 0;
//        }
////        if(loan.getRepays().size() > 0){
////            for (Repay repay : loan.getRepays()) {
////                if(repay)
////            }
////        }
//        LocalDate startDate = loan.getStartDate();//贷款开始时间
//        LocalDate endDate = loan.getEndDate();//贷款结束时间
//        if(startDate.isEqual(endDate) || startDate.isAfter(endDate)) return 0;
//        return 1;
//    }
}
