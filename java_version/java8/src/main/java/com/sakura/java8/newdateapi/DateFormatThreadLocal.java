package com.sakura.java8.newdateapi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @ClassName DateFormatThreadLocal
 * @Author Sakura
 * @DateTime 2024-06-15 11:13:57
 * @Version 1.0
 */
public class DateFormatThreadLocal {
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){

        protected DateFormat initialValue(){
            return new SimpleDateFormat("yyyyMMdd");
        }

    };

    public static final Date convert(String source) throws ParseException {
        return df.get().parse(source);
    }
}
