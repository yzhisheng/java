package com.sakura.spring5.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @ClassName Component3
 * @Author Sakura
 * @DateTime 2024-11-05 18:03:19
 * @Version 1.0
 */
@Component
@Slf4j
public class Component3 {


    @EventListener
    public void receiver(UserRegisteredEvent event){
        System.out.println("Component3接受到了事件!"+event);
        System.out.println("我开始发送邮件..........................");
//        log.info("我开始发送邮件..........................");
    }
}
