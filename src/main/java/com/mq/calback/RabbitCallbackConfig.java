/*
package com.mq.calback;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

*/
/**
 * Created by 97390 on 8/27/2018.
 *//*

public class RabbitCallbackConfig {
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitCallbackConfig.class);

    @Value("${spring.rabbitmq.host}")
    private String addresses;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;

    @Value("${rabbitmq.queuename}")
    private String queueName;

    @Value("${rabbitmq.exchange}")
    private String queueExchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Value("${cf.rabbit.service.name}")
    private String rabbitService;


    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses+":"+port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        */
/** 如果要进行消息回调，则这里必须要设置为true *//*

        connectionFactory.setPublisherConfirms(publisherConfirms);

        Channel channel = connectionFactory.createConnection().createChannel(false);

        // 声明queue,exchange,以及绑定
        try {
            channel.exchangeDeclare(queueExchange */
/* exchange名称 *//*
, "topic"*/
/* 类型 *//*
);
            channel.exchangeDeclare(queueExchange */
/* exchange名称 *//*
,"fanout"*/
/* 类型 *//*
);
            // durable,exclusive,autodelete
            channel.queueDeclare(queueName, true, false, false, null); // (如果没有就)创建Queue
            channel.queueBind(queueName, queueExchange, routingkey);
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

    // 配置接收端属性，
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // factory.setPrefetchCount(5);//这个参数设置，接收消息端，接收的最大消息数量（包括使用get、consume）,一旦到达这个数量，客户端不在接收消息。0为不限制。默认值为3.
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);// 确认模式：自动，默认
        factory.setMessageConverter(new Jackson2JsonMessageConverter());// 接收端类型转化pojo,需要序列化
        return factory;
    }

    @Bean
    */
/** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 *//*

    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplatenew() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //template.setMessageConverter(new Jackson2JsonMessageConverter());// 发送端类型转化pojo,需要序列化
        return template;
    }
}
*/
