package com.sakura.java8.optional;

import com.sakura.java8.lambda.demo1.Car;
import com.sakura.java8.lambda.demo1.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description Optional 类(java.util.Optional) 是一个容器类，代表一个值存在或不存在， 原来用 null 表示一个值不存在，现在 Optional 可以更好的表达这个概念。并且 可以避免空指针异常。
 * @ClassName TestOptional
 * @Author Sakura
 * @DateTime 2024-06-13 10:10:51
 * @Version 1.0
 * <p>
 * <p>
 * * 	Optional.of(T t) : 创建一个 Optional 实例  创建一个包含非空值的 Optional 对象。
 * * 	Optional.empty() : 创建一个空的 Optional 实例  创建一个不包含任何值的空 Optional 对象。
 * * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例  创建一个可能包含 null 值的 Optional 对象。如果 value 不为 null，则该方法会创建一个包含该值的 Optional 对象；否则，创建一个空 Optional 对象
 * * 	isPresent() : 判断是否包含值
 * <p>
 * <p>
 * * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t    接收一个参数，即为默认值。如果Optional对象中的值不为空，则返回该值，否则返回传入的默认值
 * * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值    接收的参数是一个「Supplier函数式接口」，用于在需要返回默认值时生成该值
 * orElse() 方法无论 Optional 对象是否为空都会执行，因此它总是会创建一个新的对象。orElseGet() 方法只有在 Optional 对象为空时才会执行，因此它可以用来延迟创建新的对象
 * <p>
 * * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()   接受一个函数作为参数，该函数将被应用于 Optional 对象中的值。如果 Optional 对象存在值，则将该值传递给函数进行转换，否则返回一个空 Optional 对象
 * * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 * flatMap() 方法与 map() 方法类似，都接受一个函数作为参数。但是，flatMap() 方法返回的是一个 Optional 类型的值。如果函数返回的是一个 Optional 对象，则该方法会将其"展开"，否则返回一个空 Optional 对象
 */
public class TestOptional {


    @Test
    public void test1() {
        //of方法用于创建一个非空的Optional对象，如果传入的参数为null，则会抛出NullPointerException异常。
        Optional<Employee> optional = Optional.of(new Employee());
        Employee employee = optional.get();
        System.out.println(employee);

        //TODO 可用于快速定位空指针异常位置,会抛出空指针异常
        Optional<Employee> opt = Optional.of(null);
        System.out.println(opt);
    }

    @Test
    public void test2() {
        //构建null 实例
        //ofNullable方法用于创建一个可以为空的Optional对象。如果传入的参数为空，则返回一个空的Optional对象。
        // 当 Optional 对象存在值时，调用 get() 方法可以返回该值，当 Optional 对象不存在值时，
        // 调用 get() 方法会抛出 NoSuchElementException 异常。
        Optional<Employee> opt = Optional.ofNullable(null);
        System.out.println(opt.get());
        Optional<Employee> ops = Optional.empty();
        System.out.println(ops.get());
    }

    @Test
    public void test3() {
        //Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
        Optional<Employee> op = Optional.ofNullable(null);

        //判断是否包含值
        //isPresent() : 判断是否包含值
        if (op.isPresent()) {
            System.out.println(op.get());
        }
        //为空则使用提供的默认值
        //orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
        Employee emp = op.orElse(new Employee("张三"));
        System.out.println(emp);

        //orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
        //         * Supplier<T> : 供给型接口
        //                * 		T get();
        Employee emp2 = op.orElseGet(() -> new Employee());
        System.out.println(emp2);
    }


    @Test
    public void test4() {
        //Optional.of(T t) : 创建一个 Optional 实例
        Optional<Employee> op = Optional.of(new Employee(101, "张三", 18, 9999.99));

        Optional<String> op2 = op.map(Employee::getName);
        System.out.println(op2.get());

        //要求返回值必须是 Optional类型的
        Optional<String> op3 = op.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(op3.get());
    }


    @Test
    public void test5() {
        //TODO 综合使用案例

        //当前对象可能为空
        Employee employee = null;
        System.out.println(employee == null ? "当前对象为空！" : employee.toString());
        //使用Optional
        String name = Optional.ofNullable(employee).map(Employee::getName).orElse("大哥你给我个空对象!");
        System.out.println("name = " + name);


        System.out.println("----------------------------------------------------------------------------");


        //当需要获取多个层级
        System.out.println(employee != null && employee.getCar() != null ? employee.getCar().getBrand() : "我草，你给我个空对象!");

        String brand = Optional.ofNullable(employee).map(Employee::getCar).map(Car::getBrand).orElse("没有对象啊!");
        System.out.println("brand = " + brand);
        System.out.println("-------------------------------------------------------------------------------------");
        //集合中的空数据
        // 可能包含null元素
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1, "迪迦", 12, 55.00, new Car("小米", "100")));
        list.add(new Employee(2, "大古", 99, 546, new Car(null, "100")));
        list.add(new Employee(3, "泰罗", 56, 513, new Car("奥迪", "100")));
        list.add(new Employee(4, "赛文", 87, 9655,  new Car()));
        list.add(new Employee(5, "艾斯", 25, 123, new Car("奔驰", null)));

        List<String> collect = list.stream()
                // 返回Stream<Car>
                .map(Employee::getCar)
                // 过滤掉null
                .filter(Objects::nonNull)
                // 获取street
                .map(Car::getBrand)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);

        System.out.println();

        //使用Optional
        collect = list.stream()
                // 将每个Person包装成Optional
                .map(Optional::ofNullable)
                .flatMap(op -> op.map(Employee::getCar).map(Stream::of).orElseGet(Stream::empty))
                .map(Car::getBrand)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }
}
