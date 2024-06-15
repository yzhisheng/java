package com.sakura.java8.lambda.demo1;


/**
 * @Description
 * @ClassName FilterEmployeeForSalary
 * @Author Sakura
 * @DateTime 2024-06-10 20:10:21
 * @Version 1.0
 */
public class FilterEmployeeForSalary implements MyPredicate<Employee> {
    @Override
    public boolean compare(Employee employee) {
        return employee.getSalary() >= 5000;
    }
}
