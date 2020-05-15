package com.zhong.loan;

import com.zhong.loan.pojo.Repay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Test {

    /**
     * 两个日期之间相差的年月日
     */
    @org.junit.Test
    public void testPeriod1(){
        LocalDate d1 = LocalDate.of(2020, 2, 12);
        LocalDate d2 = LocalDate.of(2021, 2, 12);

        Period period = Period.between(d1, d2);
        StringBuffer sb = new StringBuffer();
        sb.append(period.getYears()).append(",")
                .append(period.getMonths()).append(",")
                .append(period.getDays());
        System.out.println(sb.toString());
    }

    /**
     * 两个日期之间相差的天数
     */
    @org.junit.Test
    public void testPeriod2(){
        LocalDate d1 = LocalDate.of(2021, 2, 27);
        LocalDate d2 = LocalDate.of(2021, 3, 1);

        System.out.println(ChronoUnit.DAYS.between(d1, d2));
    }

    /**
     *取出两个日期之间的所有日期
     *minusDays(int i)方法为取出前 i 天的日期
     */
    @org.junit.Test
    public void getMiddleDate() {
        LocalDate d1 = LocalDate.of(2020, 3, 12);
        LocalDate d2 = LocalDate.of(2020, 3, 12);
        long length = ChronoUnit.DAYS.between(d1, d2);
        for (long i = length - 1; i >= 0; i--) { //length-1不取第一天的日期
            System.out.println(d2.minusDays(i));
        }

    }

    /**
     * 获取到每一季的20号，即3.20、6.20、9.20、12.20
     * 同时获取开始和结束日期
     */
    @org.junit.Test
    public void getSeason() {
        LocalDate d1 = LocalDate.of(2020, 3, 20);
        LocalDate d2 = LocalDate.of(2021, 6, 19);
        List<LocalDate> listDate = new ArrayList<>();
        listDate.add(d1);
        long length = ChronoUnit.DAYS.between(d1, d2);
        for (long i = length - 1; i > 0; i--) { //length-1不取第一天的日期，i>0不取最后一天的日期
            int month = d2.minusDays(i).getMonthValue();
            if(month == 3 || month == 6 || month == 9 || month == 12){
                if(d2.minusDays(i).getDayOfMonth() == 20) listDate.add(d2.minusDays(i));
            }
        }
        listDate.add(d2);

        for (int i = 0; i < listDate.size(); i++) {
            System.out.println(listDate.get(i));
        }

    }

    /**
     * 判断目标日期是否在两个日期之间，包含相等情况
     */
    @org.junit.Test
    public void betweenAndEqual(){
        LocalDate start = LocalDate.of(2020, 3, 20);
        LocalDate end = LocalDate.of(2021, 6, 19);
        LocalDate targetDate = LocalDate.of(2020, 3, 20);
        System.out.println(
                           (targetDate.isBefore(end) || targetDate.isEqual(end))
                           &&
                           (targetDate.isAfter(start) || targetDate.isEqual(start))
                          );
    }

    /**
     * 增加一年
     */
    @org.junit.Test
    public void increase(){
        LocalDate d = LocalDate.of(2020, 2, 29);
        System.out.println(d.plusYears(1));
    }

    @org.junit.Test
    public void sortRepay(){
        LocalDate d1 = LocalDate.of(2020, 3, 20);
        LocalDate d2 = LocalDate.of(2019, 3, 20);
        LocalDate d3 = LocalDate.of(2018, 3, 20);
        Repay repay1 = new Repay();
        Repay repay2 = new Repay();
        Repay repay3 = new Repay();
        repay1.setRepayDate(d1);
        repay2.setRepayDate(d2);
        repay3.setRepayDate(d3);
        List<Repay> repays = new ArrayList<>();
        repays.add(repay1);
        repays.add(repay2);
        repays.add(repay3);
        for (Repay repay : repays) {
            System.out.println(repay.getRepayDate());
        }
        System.out.println("===================================================");
        repays.sort(new Comparator<Repay>() {
            @Override
            public int compare(Repay o1, Repay o2) {
                if (o1.getRepayDate().isAfter(o2.getRepayDate())) return 1;
                if (o1.getRepayDate().isEqual(o2.getRepayDate())) return 0;
                return -1;
            }
        });
        for (Repay repay : repays) {
            System.out.println(repay.getRepayDate());
        }
    }

    @org.junit.Test
    public void fun(){
        double f = 111231.55854434;
        BigDecimal b = new BigDecimal(f);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }

    @org.junit.Test
    public void testRandom(){
        Random random = new Random();
        System.out.println(random.nextInt(8) + random.nextDouble());
    }

}
