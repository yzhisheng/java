package com.sakura.java8.defaultfunction;

/**
 * @Description
 * @ClassName MyInterface
 * @Author Sakura
 * @DateTime 2024-06-15 10:03:54
 * @Version 1.0
 */
public interface MyInterface {

    default String getName(){
        return "呵呵呵";
    }

    public static void show(){
        System.out.println("接口中的静态方法");
    }

}
