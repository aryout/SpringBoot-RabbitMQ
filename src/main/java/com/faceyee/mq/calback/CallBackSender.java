/*
package com.faceyee.mq.calback;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

*/
/**
 * Created by 97390 on 8/27/2018.
 *//*

@Component
public class CallBackSender implements  RabbitTemplate.ConfirmCallback{
    @Autowired
    private RabbitTemplate rabbitTemplatenew;
    public void send() {

        rabbitTemplatenew.setConfirmCallback(this);
        String msg="callbackSender : i am callback sender";
        System.out.println(msg );
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("callbackSender UUID: " + correlationData.getId());
        // 该示例中没有新建队列和exchange，用的是第5节中的topic.messages队列和exchange转发器。消费者也是第5节中的topicMessagesReceiver
        this.rabbitTemplatenew.convertAndSend("exchange", "topic.messages", msg, correlationData);
    }

    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // TODO Auto-generated method stub
        System.out.println("callbakck confirm: " + correlationData.getId());
    }
}*/
