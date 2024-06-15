package com.sakura.java8.lambda.test;

/**
 * @Description
 * @ClassName MyCompareFun
 * @Author Sakura
 * @DateTime 2024-06-10 22:49:41
 * @Version 1.0
 */
@FunctionalInterface
public interface MyCompareFun<T,R> {
    public R compare(T t1, T t2);
}
