package com.faceyee.mq;

import com.faceyee.mq.fanout.FanoutSender;
import com.faceyee.mq.helloDemo.HelloSender;
import com.faceyee.mq.topic.TopicSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 97390 on 8/27/2018.
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    @Autowired
    private HelloSender helloSender;
    @Autowired
    private TopicSender topicSender;

/*    @Autowired
    private CallBackSender callBackSender;*/

    @Autowired
    private FanoutSender fanoutSender;

    @PostMapping("/hello") // 一对多
    public void hello() {
        helloSender.send();
    }


    /**
     * topic exchange类型rabbitmq测试
     */
    @PostMapping("/topic")
    public void topicTest() {
        topicSender.send1();
        topicSender.send2();
    }

/*
    @PostMapping("/callback")
    public void callbak() {
        callBackSender.send();
    }
*/

    /**
     * fanout exchange类型rabbitmq测试
     */
    @PostMapping("/fanoutTest")
    public void fanoutTest() {
        fanoutSender.send();
    }
}
