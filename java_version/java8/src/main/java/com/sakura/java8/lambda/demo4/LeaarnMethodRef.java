package com.sakura.java8.lambda.demo4;

import com.sakura.java8.lambda.demo1.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.function.*;

/**
 * @Description 方法引用与构造器引用
 * @ClassName LeaarnMethodRef
 * @Author Sakura
 * @DateTime 2024-06-13 09:22:39
 * @Version 1.0
 * <p>
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * <p>
 * 1. 对象的引用 :: 实例方法名
 * <p>
 * 2. 类名 :: 静态方法名
 * <p>
 * 3. 类名 :: 实例方法名
 * <p>
 * 注意：
 * ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 * <p>
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 * <p>
 * 1. 类名 :: new
 * <p>
 * 三、数组引用
 * <p>
 * 类型[] :: new;
 */
public class LeaarnMethodRef {

    @Test
    public void test1() {
        //TODO 对象的引用 :: 实例方法名
        //方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
        PrintStream out = System.out;
        Consumer<String> con = (str) -> out.println(str);
        con.accept("hello world!");

        System.out.println("--------------------------------");
        //使用对象引用
        Consumer<String> con2 = out::println;
        con2.accept("Hello Java8！");

        System.out.println("--------------------------------");

        Consumer<String> con3 = System.out::println;
        con3.accept("我草！");

        System.out.println("--------------------------------");
        //来个实际案例
        Employee emp = new Employee(101, "张三", 18, 9999.99);

        //通过对象.获取
        Supplier<String> sup = () -> emp.getName();
        System.out.println(sup.get());

        System.out.println("----------------------------------");

        Supplier<String> sup2 = emp::getName;
        System.out.println(sup2.get());
    }

    @Test
    public void test2() {
        //TODO 类名 :: 静态方法名
        BiFunction<Double, Double, Double> fun = (x, y) -> Math.max(x, y);
        System.out.println(fun.apply(1.5, 22.2));

        System.out.println("--------------------------------------------------");

        BiFunction<Double, Double, Double> fun2 = Math::min;
        System.out.println(fun2.apply(1.2, 1.5));

    }

    @Test
    public void test3() {
        //TODO 类名 :: 实例方法名
        //若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("abcde", "abcde"));

        System.out.println("-----------------------------------------");

        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("abc", "abc"));

        System.out.println("-----------------------------------------");


        Function<Employee, String> fun = (e) -> e.show();
        System.out.println(fun.apply(new Employee()));

        System.out.println("-----------------------------------------");

        Function<Employee, String> fun2 = Employee::show;
        System.out.println(fun2.apply(new Employee()));

    }

    @Test
    public void test4(){

        //TODO 类名 :: new  构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
        Supplier<Employee> sup = () -> new Employee();
        System.out.println(sup.get());

        System.out.println("------------------------------------");

        Supplier<Employee> sup2 = Employee::new;
        System.out.println(sup2.get());


        System.out.println("------------------------------------");
        //当要实现的函数有几个入参，就会匹配对应的构造器
        Function<String, Employee> fun = Employee::new;
        System.out.println(fun.apply("abc"));

        BiFunction<String, Integer, Employee> fun2 = Employee::new;


    }

    @Test
    public void test5(){

        //TODO 数组引用 类型[] :: new;
        Function<Integer, String[]> fun = (args) -> new String[args];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        System.out.println("--------------------------");

        Function<Integer, Employee[]> fun2 = Employee[] :: new;
        Employee[] emps = fun2.apply(20);
        System.out.println(emps.length);
    }

}
