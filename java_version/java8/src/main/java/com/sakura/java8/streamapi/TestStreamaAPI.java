package com.sakura.java8.streamapi;

import com.sakura.java8.lambda.demo1.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description
 * @ClassName TestStreamaAPI
 * @Author Sakura
 * @DateTime 2024-06-14 20:52:21
 * @Version 1.0
 * * 一、Stream API 的操作步骤：
 * *
 * * 1. 创建 Stream
 * *
 * * 2. 中间操作
 * *
 * * 3. 终止操作(终端操作)
 */
public class TestStreamaAPI {

    //TODO 创建Stream
    @Test
    public void test1() {

        //1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个顺序流
        //获取一个并行流
        Stream<String> parallelStream = list.parallelStream();

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] nums = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(nums);

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5, 6);

        //4. 创建无限流
        //迭代,从0开始等差加2
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        //生成，生成2个随机数
        Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
        stream4.forEach(System.out::println);
    }

    //TODO 中间操作
    @Test
    public void test2() {
        //内部迭代：迭代操作 Stream API 内部完成
        //所有的中间操作不会做任何的处理
        Stream<Employee> stream = emps.stream()
                .filter((e) -> {
                    System.out.println("测试中间操作");
                    return e.getAge() <= 35;
                });

        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        stream.forEach(System.out::println);

        //外部迭代
        Iterator<Employee> it = emps.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    /*
        筛选与切片
        filter——接收 Lambda ， 从流中排除某些元素。
        limit——截断流，使其元素不超过给定数量。
        skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
        distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
    */
    @Test
    public void test3() {
        //filter 过滤
        emps.stream()
                .filter((e) -> {
                    System.out.println("短路！"); // &&  ||
                    return e.getSalary() >= 5000;
                }).limit(3)
                .forEach(System.out::println);


        //skip(n) —— 跳过元素 跳过前两个
        emps.parallelStream()
                .filter((e) -> e.getSalary() >= 5000)
                .skip(2)
                .forEach(System.out::println);


        //distinct——筛选 数据去重
        emps.stream()
                .distinct()
                .forEach(System.out::println);

    }

    //TODO 中间操作
	/*
		映射
		map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
    @Test
    public void test4() {
        Stream<String> str = emps.stream()
                .map((e) -> e.getName());

        System.out.println("-------------------------------------------");

        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        //map
        strList.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);


        Stream<Stream<Character>> stream2 = strList.stream()
                .map(TestStreamaAPI::filterCharacter);

        stream2.forEach((sm) -> {
            sm.forEach(System.out::println);
        });

        System.out.println("---------------------------------------------");

        //flatMap  扁平化处理，将得到的Stream<Stream<Character>>处理为Stream<Character>
        Stream<Character> stream3 = strList.stream()
                .flatMap(TestStreamaAPI::filterCharacter);

        stream3.forEach(System.out::println);
    }

    //TODO 中间操作
    /*
        sorted()——自然排序
        sorted(Comparator com)——定制排序
     */
    @Test
    public void test5() {
        emps.stream()
                .map(Employee::getName)
                .sorted()
                .forEach(System.out::println);

        System.out.println("------------------------------------");

        emps.stream()
                .sorted((x, y) -> {
                    if (x.getAge() == y.getAge()) {
                        return x.getName().compareTo(y.getName());
                    } else {
                        return Integer.compare(x.getAge(), y.getAge());
                    }
                }).forEach(System.out::println);
    }

    //TODO 终止操作
    /*
		allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否没有匹配的元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
	 */
    @Test
    public void test6() {
        //allMatch——检查是否匹配所有元素
        boolean bl = emps.stream()
                .allMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(bl);

        //anyMatch——检查是否至少匹配一个元素
        boolean bl1 = emps.stream()
                .anyMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(bl1);

        //noneMatch——检查是否没有匹配的元素
        boolean bl2 = emps.stream()
                .noneMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(bl2);

        //findFirst——返回第一个元素
        Optional<Employee> op = emps.stream()
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();
        System.out.println(op.get());

        //findAny——返回当前流中的任意元素
        Optional<Employee> op2 = emps.parallelStream()
                .filter((e) -> e.getStatus().equals(Employee.Status.FREE))
                .findAny();
        System.out.println(op2.get());

        //count——返回流中元素的总个数
        long count = emps.stream()
                .filter((e) -> e.getStatus().equals(Employee.Status.FREE))
                .count();
        System.out.println(count);

        //max——返回流中最大值
        Optional<Double> op3 = emps.stream()
                .map(Employee::getSalary)
                .max(Double::compare);
        System.out.println(op3.get());

        //min——返回流中最小值
        Optional<Employee> op4 = emps.stream()
                .min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(op4.get());

    }

    //TODO 终止操作
	/*
		归约
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
    @Test
    public void test7() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //集合中元素之和
        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        System.out.println("----------------------------------------");

        //员工工资之和
        Optional<Double> op = emps.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op.get());
    }

    //TODO 终止操作
    /*
        collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void test8() {
        //收集成list
        List<String> list = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("----------------------------------");

        //收集成set
        Set<String> set = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("----------------------------------");

        //收集成HashSet
        HashSet<String> hs = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hs.forEach(System.out::println);

        System.out.println("----------------------------------");

        //总数
        Long count = emps.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        System.out.println("----------------------------------");

        //平均值
        Double avg = emps.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        System.out.println("----------------------------------");

        //总和
        Double sum = emps.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        System.out.println("----------------------------------");

        //最大值
        Optional<Double> max = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.maxBy(Double::compare));
        System.out.println(max.get());

        //最小值
        Optional<Employee> op = emps.stream()
                .collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(op.get());

        System.out.println("--------------------------------------------");

      /*  Java中的DoubleStream summaryStatistics()方法可以方便地获取DoubleStream流中元素的摘要统计信息，包括数量、总和、平均值、最小值和最大值。
        这些统计信息可以帮助程序员更好地理解流中元素的特征，方便进行后续的操作和计算。
        要注意的是，summaryStatistics()方法仅适用于DoubleStream，而不适用于其他类型的流*/
        DoubleSummaryStatistics dss = emps.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getMax());

        System.out.println("--------------------------------------------");

        //分组
        Map<Employee.Status, List<Employee>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);

        System.out.println("--------------------------------------------");

        //多级分组
        Map<Employee.Status, Map<String, List<Employee>>> map1 = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if (e.getAge() >= 60)
                        return "老年";
                    else if (e.getAge() >= 35)
                        return "中年";
                    else
                        return "成年";
                })));
        System.out.println(map1);

        System.out.println("--------------------------------------------");

        //分区
        Map<Boolean, List<Employee>> map2 = emps.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
        System.out.println(map2);

        System.out.println("--------------------------------------------");

        //连接
        String str = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.joining("," , "----", "----"));
        System.out.println(str);

        System.out.println("--------------------------------------------");

        //规约
        Optional<Double> sum1 = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.reducing(Double::sum));
        System.out.println(sum1.get());
    }

    //TODO 终止操作
    /*
        grep -分组
     */
    @Test
    public void test9() {

    }


    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }

        return list.stream();
    }


    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66, Employee.Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Employee.Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(105, "田七", 38, 5555.55, Employee.Status.BUSY)
    );
}
