package com.sakura.spring5.demo;


import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
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


        /*3.ApplicationContext比BeanFactory多点啥*/

        //MessageSource  处理国际化资源
        System.out.println(context.getMessage("hi",null, Locale.CHINESE));//处理国际化资源
        System.out.println(context.getMessage("hi",null, Locale.ENGLISH));//处理国际化资源

        //ResourcePatternResolver  通配符访问资源
        Resource[] resources = context.getResources("classpath:application.properties");//到类路径下找文件
        for (Resource resource : resources) {
            System.out.println(resource);
        }
        resources = context.getResources("classpath*:META-INF/spring.factories");//到类和jar包路径下找文件
        for (Resource resource : resources) {
            System.out.println(resource);
        }
//        context.getResource("file:sample.txt");//到磁盘路径下找文件


        //Environment 处理环境配置信息
        System.out.println(context.getEnvironment().getProperty("java_home"));
        System.out.println(context.getEnvironment().getProperty("server.port"));

        //ApplicationEventPublisher  发布事件对象
//        context.publishEvent(new UserRegisteredEvent(context));
        //事件可用于业务逻辑解耦
        context.getBean(Component1.class).register();
    }

}
