package com.sakura.java8.lambda.demo1;

/**
 * @Description
 * @ClassName MyPredicate
 * @Author Sakura
 * @DateTime 2024-06-10 20:07:58
 * @Version 1.0
 */
public interface MyPredicate<T> {
    public boolean compare(T t);
}
