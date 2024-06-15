package com.sakura.java8.defaultfunction;

/**
 * @Description
 * @ClassName SubClass
 * @Author Sakura
 * @DateTime 2024-06-15 09:57:32
 * @Version 1.0
 */
public class SubClass /*extends MyClass*/ implements MyFun,MyInterface{

    @Override
    public String getName() {
        //当用同名方法需要实现，必须指定试下哪个接口
//        return MyInterface.super.getName();
        return MyFun.super.getName();
    }
}
