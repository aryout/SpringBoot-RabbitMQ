package com.faceyee.mq.helloDemo;

/**
 * Created by 97390 on 8/26/2018.
 */

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接受者，接受者与发送者的queue必须一致才可以接收到信息
 */
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {

    @RabbitHandler
    public void process(String hello){
        System.out.println("Receiver1:" + hello);
    }

}