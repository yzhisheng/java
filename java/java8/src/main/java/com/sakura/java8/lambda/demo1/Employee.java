package com.sakura.java8.lambda.demo1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description
 * @ClassName Employee
 * @Author Sakura
 * @DateTime 2024-06-10 19:55:05
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    private int id;
    private String name;
    private int age;
    private double salary;

    private Car car;

    private Status status;

    public Employee(int id, String name, int age, double salary, Status status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.status = status;
    }

    public Employee(int id, String name, int age, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String s, Integer integer) {
        this.age = integer;
        this.name = s;
    }

    public String show() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Salary: " + salary;
    }

    public enum Status {
        FREE, BUSY, VOCATION;
    }
}
