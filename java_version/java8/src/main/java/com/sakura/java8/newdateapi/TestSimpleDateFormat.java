package com.sakura.java8.newdateapi;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description
 * @ClassName TestSimpleDateFormat
 * @Author Sakura
 * @DateTime 2024-06-15 10:36:09
 * @Version 1.0
 */
public class TestSimpleDateFormat {

    //java.util.Date 存在线程安全问题
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        //TODO 存在线程安全问题
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Callable<Date> task = new Callable<Date>() {

            @Override
            public Date call() throws Exception {
                return sdf.parse("20161121");
            }

        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }

        for (Future<Date> future : results) {
            System.out.println(future.get());
        }

        //关闭线程池
        pool.shutdown();
    }

    //TODO 使用传统方式解决线程安全问题   ThreadLocal
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        //TODO 使用传统方式解决线程安全问题   ThreadLocal

        Callable<Date> task = new Callable<Date>() {

            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("20161121");
            }

        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }

        for (Future<Date> future : results) {
            System.out.println(future.get());
        }

        pool.shutdown();

    }



}
