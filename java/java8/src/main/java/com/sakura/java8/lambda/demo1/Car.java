package com.sakura.java8.lambda.demo1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description
 * @ClassName Car
 * @Author Sakura
 * @DateTime 2024-06-13 10:52:53
 * @Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Car {

    private String brand;
    private String price;

}
