package com.mq.helloDemo;

/**
 * Created by 97390 on 8/26/2018.
 */

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 发送者
 * 依靠hello字段将消息平均分发给消费者
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(){
        String context = "hello" + new Date();
        System.out.println("sender:" + context);
        this.rabbitTemplate.convertAndSend("helloDemo",context);
    }

    public void send(int i) {
        String context = "hello,ZZP" + i;
        System.out.println("sender:" + context);
        this.rabbitTemplate.convertAndSend("helloDemo",context);
    }

}