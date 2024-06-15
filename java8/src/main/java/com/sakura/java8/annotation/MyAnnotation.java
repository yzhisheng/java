package com.sakura.java8.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @Description
 * @ClassName MyAnnotation
 * @Author Sakura
 * @DateTime 2024-06-15 12:43:22
 * @Version 1.0
 */
@Repeatable(MyAnnotations.class) //指定注解容器
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME) //生命周期
public @interface MyAnnotation {

    String value() default "我是MyAnnotation ";

}
