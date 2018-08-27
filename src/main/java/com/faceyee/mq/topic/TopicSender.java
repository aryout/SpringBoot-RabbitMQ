package com.faceyee.mq.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 97390 on 8/26/2018.
 */
@Component
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send1(){
        String context = "hello,i am topic.mesaag message:";
        System.out.println("Sender: =================" + context);
        this.rabbitTemplate.convertAndSend("topicExchange","topic.message",context);
    }

    public void send2(){
        String context = "hello,i am topic.mesaages:";
        System.out.println("Sender: =================" + context);
        this.rabbitTemplate.convertAndSend("topicExchange","topic.messages",context);
    }
}