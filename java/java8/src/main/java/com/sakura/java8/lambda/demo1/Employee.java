package com.sakura.java8.lambda.demo1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Employee {

    private int id;
    private String name;
    private int age;
    private double salary;
}
