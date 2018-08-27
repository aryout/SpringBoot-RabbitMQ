package com.faceyee.mq.topic;

/**
 * Created by 97390 on 8/26/2018.
 */

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* topic 是RabbitMQ中最灵活的一种方式，可以根据binding_key自由的绑定不同的队列

首先对topic规则配置，这里使用两个队列来测试（也就是在Application类中创建和绑定的topic.message和topic.messages两个队列），其中topic.message的bindting_key为

“topic.message”，topic.messages的binding_key为“topic.#”；
*
* */
/**
 * topic规则配置，根据routing_key自由的绑定不同的队列
 * 使用queuemessages同时匹配两个队列，queuemessage之匹配"topic。message"队列
 */
@Configuration
public class TopicRabbitConfig {

    final static String message = "topic.message";
    final static String messages = "topic.messages";

    @Bean
    public Queue quequeMessage(){
        return new Queue(TopicRabbitConfig.message);
    }

    @Bean
    public Queue queueMessages(){
        return new Queue(TopicRabbitConfig.messages);
    }

    @Bean // 不需要转发器
    public Queue queueHello(){
        return new Queue("hello");
    }

    @Bean  // 不需要转发器
    public Queue queuesObject(){
        return new Queue("object");
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange("topicExchange");
    } // 定义转发器

    @Bean
    Binding bindingExchangeMessage(Queue quequeMessage, TopicExchange exchange){ // quequeMessage 是定义好的队列,它有名字,名字通过绑定的key匹配; exchange是定义好的转发器,队列绑定到这个转发器上
        return BindingBuilder.bind(quequeMessage).to(exchange).with("topic.message"); // quequeMessage队列,绑定到exchange转发器,键为topic.message,精确匹配
        // 将队列绑定到转发器上,指定该转发器上的某类消息自己才接收,不符合的不接收;显然,每个队列都得绑定,都得生成一个bean
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages,TopicExchange exchange){
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#"); // quequeMessages队列,绑定到exchange转发器,键为topic.#,即转发器上topic.message和topic.messages的key类型消息都接收

        // this.rabbitTemplate.convertAndSend("exchange", "topic.message", msg1);在生产者发送消息时,第一个参数是指定转发器,第二个参数是指定这次发送的消息的绑定路由键.
        // 绑定到这个转发器上的消费者再根据这个键匹配这个消息是否自己能接收到,而没绑定在这个转发器上的消费者肯定不能接收到
    }
}