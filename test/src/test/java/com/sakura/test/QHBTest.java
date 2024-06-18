package com.sakura.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description
 * @ClassName QHBTest
 * @Author Sakura
 * @DateTime 2024-06-16 16:44:31
 * @Version 1.0
 */
public class QHBTest {
    public static final String DATESTYLE_0 = "yyyy-MM-dd";

    public String FormatDate(Date date, String sf) {
        if (date == null)
            return "";
        SimpleDateFormat dateformat = new SimpleDateFormat(sf);
        return dateformat.format(date);
    }

    //取系统时间时一定要用这个方法，否则日期可能不动
    public Date getCurrDateTime() {
        return new Date(System.currentTimeMillis());
    }

    public String getCurrDate() {
        Date date_time = getCurrDateTime();
        return FormatDate(date_time, "yyyy-MM-dd");
    }

    public String addMonth(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATESTYLE_0);

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            //            cd.add(Calendar.DATE, n);// 增加一天
            cd.add(Calendar.MONTH, n);//增加一个月
            //减一秒
            //减一秒
            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }
    }

    @Test
    public void test() {
        String currDate = getCurrDate();//当前系统时间
        System.out.println(currDate);
        System.out.println("----------------------------------");
        String nextDate = addMonth(currDate, 1);//下次日期
        System.out.println(nextDate);

        System.out.println();
        String str = "{\"rules\":[{\"1\":1}]}";
        str = "{\"rules\":[{\"1\":1},{\"2\":0},{\"3\":0}]}";
        int deductCount = 0;
        com.alibaba.fastjson.JSONArray rulesArray = JSON.parseObject(str).getJSONArray("rules");

        for (int i = 0; i < rulesArray.size(); i++) {

            if (deductCount < rulesArray.size()) {

                Integer day = Integer.parseInt(rulesArray.getJSONObject(deductCount).getString(String.valueOf(deductCount + 1)));//下次扣款时间
                //日期计算公式= 当前日期+月份
                deductCount++;
                System.out.println("第" + deductCount + "次扣款日期：");

                System.out.println(currDate);
                System.out.println("----------------------------------");
                nextDate = addMonth(currDate, day);//下次日期
                currDate = nextDate;
                System.out.println(nextDate);

            }

        }


    }
}
