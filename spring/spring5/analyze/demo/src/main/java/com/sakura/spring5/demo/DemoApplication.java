package com.sakura.spring5.demo;


import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
/*		1.到底什么是BeanFactory
		● 它是ApplicationContext的父接口
		● 它才是Spring的核心容器，主要的ApplicationContext实现都【组合】了它的功能*/
        System.out.println(context);


/*		2.BeanFactory能干啥
		● 表面上只有getBean
		● 实际上控制反转，基本的依赖注入，直至Bean的生命周期的各种功能，都由它的实现类提供*/
        //使用反射获取被Spring容器管理的两个类,Component1，Component2
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        map.keySet().stream().filter(key -> key.startsWith("component")).forEach(key -> {
            System.out.println(key + "===" + map.get(key));
        });



    }

}
