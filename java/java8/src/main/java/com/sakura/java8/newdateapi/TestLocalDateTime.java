package com.sakura.java8.newdateapi;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @Description
 * @ClassName TestLocalDateTime
 * @Author Sakura
 * @DateTime 2024-06-15 11:09:49
 * @Version 1.0
 */
public class TestLocalDateTime {

    //TODO 新日期时间Api
    //TODO 1. LocalDate、LocalTime、LocalDateTime
    //LocalDate、LocalTime、LocalDateTime 类的实 例是不可变的对象，分别表示使用 ISO-8601日 历系统的日期、时间、日期和时间。
    // 它们提供 了简单的日期或时间，并不包含当前的时间信 息。也不包含与时区相关的信息。
    @Test
    public void test3() {
        //每次操作都是返回一个新实例
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ld2 = LocalDateTime.of(2016, 11, 21, 10, 10, 10);
        System.out.println(ld2);

        LocalDateTime ldt3 = ld2.plusYears(20);
        System.out.println(ldt3);

        LocalDateTime ldt4 = ld2.minusMonths(2);
        System.out.println(ldt4);

        System.out.println(ldt.getYear());
        System.out.println(ldt.getMonthValue());
        System.out.println(ldt.getDayOfMonth());
        System.out.println(ldt.getHour());
        System.out.println(ldt.getMinute());
        System.out.println(ldt.getSecond());
    }

    //TODO 2. Instant(计算机读的时间) : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
    //用于“时间戳”的运算。它是以Unix元年(传统 的设定为UTC时区1970年1月1日午夜时分)开始 所经历的描述进行运算
    @Test
    public void test4() {
        /* 默认使用 UTC 时区 */
        Instant ins = Instant.now();
        System.out.println(ins);

        //偏移量时区
        OffsetDateTime odt = ins.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        //获取毫秒
        System.out.println(ins.toEpochMilli());
        //获取纳秒
        System.out.println(ins.getNano());

        //+5s
        Instant ins2 = Instant.ofEpochSecond(5);
        System.out.println(ins2);
    }

    //TODO 3.计算日期间隔
    //  Duration : 用于计算两个“时间”间隔
    //	Period : 用于计算两个“日期”间隔
    @Test
    public void test5() {
        //当前时间戳
        Instant ins1 = Instant.now();

        System.out.println("--------------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        Instant ins2 = Instant.now();

        Duration dration = Duration.between(ins1, ins2);

        System.out.println("Instant所耗费时间为：" + dration.toMillis() + "ms");

        //具体时间
        LocalDateTime lt1 = LocalDateTime.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        LocalDateTime lt2 = LocalDateTime.now();
        System.out.println("LocalDateTime所耗费时间为：" + Duration.between(lt1, lt2).toMillis() + "ms");


        System.out.println("----------------------------------");

        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2011, 1, 1);
        //计算相差时间
        Period pe = Period.between(ld2, ld1);
        System.out.println(pe.getYears());
        System.out.println(pe.getMonths());
        System.out.println(pe.getDays());
    }

    //TODO 4. TemporalAdjuster : 时间校正器
    //TemporalAdjuster : 时间校正器。有时我们可能需要获 取例如：将日期调整到“下个周日”等操作。
    //TemporalAdjusters: 该类通过静态方法提供了大量的常 用 TemporalAdjuster 的实现。
    @Test
    public void test6() {
        //当前系统时间
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        //指定月中的天
        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);

        //获取下个周日
        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        //自定义：下一个工作日
        LocalDateTime ldt5 = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;
            //获取工作日
            DayOfWeek dow = ldt4.getDayOfWeek();

            //自定义日期运算
            if (dow.equals(DayOfWeek.FRIDAY)) {
                return ldt4.plusDays(3);//周五 加3天
            } else if (dow.equals(DayOfWeek.SATURDAY)) {
                return ldt4.plusDays(2); //周六 加2天
            } else {
                return ldt4.plusDays(1);
            }
        });

        System.out.println(ldt5);
    }

    //TODO 5. DateTimeFormatter : 解析和格式化日期或时间
    @Test
    public void test7() {
//		DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");

        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);

        System.out.println(strDate);

        //字符串转换为LocalDateTime
        LocalDateTime newLdt = ldt.parse(strDate, dtf);
        System.out.println(newLdt);
    }

    //TODO 6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
//    ava8 中加入了对时区的支持，带时区的时间为分别为： ZonedDate、ZonedTime、ZonedDateTime 其中每个时区都对应着 ID，地区ID都为 “{区域}/{城市}”的格式 例如 ：Asia/Shanghai 等
//    ZoneId：该类中包含了所有的时区信息 getAvailableZoneIds() : 可以获取所有时区时区信息 of(id) : 用指定的时区信息获取ZoneId 对象
    @Test
    public void test8() {
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(zdt);
    }











}
