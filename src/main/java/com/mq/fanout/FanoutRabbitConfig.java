package com.mq.fanout;

/**
 * Created by 97390 on 8/26/2018.
 */

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 广播模式或者订阅模式，给fanoutExchange转发器发送消息，绑定了这个交换机的所有队列都收到这个消息
 * 这里使用了ABC三个队列绑定到fanoutExchange转发器上面,发送端的routing_key写任何字符都会被忽略
 * 算fanoutSender发送消息的时候，指定了routing_key为"abcd.ee"，但是所有接收者都接受到了消息
 */
@Configuration
public  class FanoutRabbitConfig {
    private final Logger LOGGER = LoggerFactory.getLogger(FanoutRabbitConfig.class);

/*    @Bean
    public Queue aMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue bMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue cMessage() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA(Queue aMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(aMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue bMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(bMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue cMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(cMessage).to(fanoutExchange);
    }*/

    @Bean
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();

        Channel channel = connectionFactory.createConnection().createChannel(false);

        // 声明queue,exchange,以及绑定
        try {
            channel.exchangeDeclare("fanoutExchange","fanout");
            channel.queueDeclare("fanout.A", true, false, false, null); // (如果没有就)创建Queue
            channel.queueDeclare("fanout.B", true, false, false, null); // (如果没有就)创建Queue
            channel.queueDeclare("fanout.C", true, false, false, null); // (如果没有就)创建Queue
            channel.queueBind("fanout.A", "fanoutExchange", "");
            channel.queueBind("fanout.B", "fanoutExchange", "");
            channel.queueBind("fanout.C", "fanoutExchange", "");
        } catch (Exception e) {
            LOGGER.error("mq declare queue exchange fail ", e);
        } finally {
            try {
                channel.close();
            } catch (Exception e) {
                LOGGER.error("mq channel close fail", e);
            }
        }

        return connectionFactory;
    }

}