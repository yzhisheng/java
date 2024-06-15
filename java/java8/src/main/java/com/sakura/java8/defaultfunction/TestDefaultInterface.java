package com.sakura.java8.defaultfunction;

/**
 * @Description
 * @ClassName TestDefaultInterface
 * @Author Sakura
 * @DateTime 2024-06-15 09:56:45
 * @Version 1.0
 */
public class TestDefaultInterface {

    public static void main(String[] args) {

       //TODO 接口中的默认方法

        /*
            接口默认方法的”类优先”原则若一个接口中定义了一个默认方法，而另外一个父类或接口中又定义了一个同名的方法时
            选择父类中的方法。如果一个父类提供了具体的实现，那么接口中具有相同名称和参数的默认方法会被忽略。接口冲突。
            如果一个父接口提供一个默认方法，而另一个接口也提供了一个具有相同名称和参数列表的方法(不管方法是否是默认方法)，
            那么必须覆盖该方法来解决冲突
        */
        SubClass sc = new SubClass();
        System.out.println(sc.getName());

        //接口中的静态类
        MyInterface.show();

    }

}
