package com.sakura.spring5.demo;

import org.springframework.context.ApplicationEvent;

/**
 * @Description
 * @ClassName UserRegisteredEvent
 * @Author Sakura
 * @DateTime 2024-11-05 17:49:37
 * @Version 1.0
 */
public class UserRegisteredEvent extends ApplicationEvent {
    public UserRegisteredEvent(Object source) {
        super(source);
    }
}
