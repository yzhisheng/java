package com.sakura.java8.lambda.test;

/**
 * @Description
 * @ClassName MyFun
 * @Author Sakura
 * @DateTime 2024-06-10 22:23:14
 * @Version 1.0
 */

// * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
// * 			 可以检查是否是函数式接口
@FunctionalInterface
public interface MyFun {

    public String getValue(String num);

}
