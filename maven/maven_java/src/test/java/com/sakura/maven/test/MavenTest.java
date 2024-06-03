package com.sakura.maven.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @Author Sakura
 * @Date 2024/6/3 16:47
 * @Version 1.0
 */
public class MavenTest {
    @Test
    public void testAssert(){
        int a = 10;
        int b = 20;
        Assertions.assertEquals(a+b, 30);
        System.out.println("Hello world!");
    }
}
