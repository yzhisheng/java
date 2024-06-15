package com.sakura.java8.defaultfunction;

/**
 * @Description
 * @ClassName MyFun
 * @Author Sakura
 * @DateTime 2024-06-15 09:51:47
 * @Version 1.0
 */
public interface MyFun {

    //default 修饰的为默认方法，可以直接在接口中返回
    default String getName(){
        return "哈哈哈";
    }

}
