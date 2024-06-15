package com.sakura.java8.annotation;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Description 重复注解与类型注解
 * @ClassName TestAnnotation
 * @Author Sakura
 * @DateTime 2024-06-15 12:43:12
 * @Version 1.0
 */
public class TestAnnotation {

    @Test
    public void test1() throws NoSuchMethodException {
        Class<TestAnnotation> clazz  = TestAnnotation.class;
        Method show = clazz.getMethod("show");

        MyAnnotation[] annotationsByType = show.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation annotation : annotationsByType) {
            System.out.println(annotation);
        }

    }


    //定义重复注解
    @MyAnnotation("Hello")
    @MyAnnotation("World")
    public void show(@MyAnnotation("abc") String str) {

    }

}
