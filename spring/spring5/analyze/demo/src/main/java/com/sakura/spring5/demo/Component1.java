package com.sakura.spring5.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @ClassName Content1
 * @Author Sakura
 * @DateTime 2024-11-05 16:54:04
 * @Version 1.0
 */
@Component
@Slf4j
public class Component1 {

    @Autowired
    private ApplicationEventPublisher publisher;

    public void register(){
//        log.info("register完成.....................");
        System.out.println("register完成.....................");
        //发送事件，通知发短信或邮件;具体由接收事件的方法处理
        publisher.publishEvent(new UserRegisteredEvent(this));
    }
}
