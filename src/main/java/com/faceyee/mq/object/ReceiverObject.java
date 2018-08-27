package com.faceyee.mq.object;

import com.faceyee.domain.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by 97390 on 8/26/2018.
 */
@Component
@RabbitListener(queues = "user")
public class ReceiverObject {

    @RabbitHandler
    public void process(User user){
        System.out.println("object user:" + user);
    }
}