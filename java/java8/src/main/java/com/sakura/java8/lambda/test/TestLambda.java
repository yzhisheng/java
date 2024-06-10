package com.sakura.java8.lambda.test;

import com.sakura.java8.lambda.demo1.Employee;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @ClassName TestLambda
 * @Author Sakura
 * @DateTime 2024-06-10 22:30:04
 * @Version 1.0
 */
public class TestLambda {


    @Test
    public void test01() {
        //调用 Collections.sort()方法，通过定制排序比较两个 Employee(先按年龄比，年龄相同按姓名比)，使用 Lambda 作为参数传递。。

        Collections.sort(emps, (x, y) -> {
            if (x.getAge() == y.getAge()) {
                return x.getName().compareTo(y.getName());
            }

            return Integer.compare(x.getAge(), y.getAge());
        });

        emps.forEach(System.out::println);

    }

    @Test
    public void test02() {
//        ①声明函数式接口，接口中声明抽象方法，publicstringgetValue(string str);
//        ②声明类 TetLambda ，类中编写方法使用接口作为参数，将一个字符串转换成大写,并作为方法的返回值。
//        ③再将一个字符串的第2个和第4个索引位置进行截取子串。

        String trimStr = strHandler("\t\t\t\t 我草，你是大水逼", (str) -> str.trim());
        System.out.println("trimStr = " + trimStr);

        String upperStr = strHandler("abcdefg", (str) -> str.toUpperCase());
        System.out.println("upperStr = " + upperStr);

        String subStr = strHandler("是他是他，就是他，我们的小英雄，小哪吒", (str) -> str.substring(16, 19));
        System.out.println("subStr = " + subStr);

    }


    @Test
    public void test03() {
//        ①声明一个带两个泛型的函数式接口，泛型类型为<T,R> T 为参数，R 为返回值。
//        ②接口中声明对应抽象方法。
//        ③在 Jestlambda 类中声明方法，使用接口作为参数，计算两个 long 型参数的和。
//        ④再计算两个 long 型参数的乘积。

        operation(100L, 200L, (x, y) -> x + y);
        operation(100L, 200L, (x, y) -> x * y);

    }

    public void operation(Long l1, Long l2, MyCompareFun<Long, Long> compareFun) {
        System.out.println(compareFun.compare(l1, l2));
    }


    public String strHandler(String str, MyFun myFun) {
        return myFun.getValue(str);
    }


    List<Employee> emps = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(106, "马大哈", 28, 4444.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );
}
