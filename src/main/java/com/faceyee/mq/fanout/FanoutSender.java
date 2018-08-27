package com.faceyee.mq.fanout;

/**
 * Created by 97390 on 8/26/2018.
 */

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这里使用的channel在分配的时候没有生效
 */
@Component
public class FanoutSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String context = "hi,fanout msg";
        System.out.println("Sender Fanout:" + context);
        this.amqpTemplate.convertAndSend("fanoutExchange","",context);// "fanoutExchange" 在config中定义
    }
}