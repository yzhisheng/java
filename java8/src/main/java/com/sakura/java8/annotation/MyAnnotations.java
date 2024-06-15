package com.sakura.java8.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;

/**
 * @Description
 * @ClassName NewAnnotation
 * @Author Sakura
 * @DateTime 2024-06-15 12:46:51
 * @Version 1.0
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME) //生命周期
public @interface MyAnnotations {

    MyAnnotation[] value();
}
