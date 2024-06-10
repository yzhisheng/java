package com.sakura.java8.lambda.demo1;

/**
 * @Description
 * @ClassName FilterEmployeeForAge
 * @Author Sakura
 * @DateTime 2024-06-10 20:11:08
 * @Version 1.0
 */
public class FilterEmployeeForAge implements MyPredicate<Employee> {
    @Override
    public boolean compare(Employee employee) {
        return employee.getAge() <= 35;
    }
}
