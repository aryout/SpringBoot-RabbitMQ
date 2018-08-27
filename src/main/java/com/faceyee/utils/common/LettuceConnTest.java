package com.faceyee.utils.common;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.concurrent.ExecutionException;

/**
 * Created by 97390 on 8/24/2018.
 */
public class LettuceConnTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RedisURI redisURI1 = RedisURI.builder().withHost("127.0.0.1").withPort(6379).withPassword("964988").build();
        RedisURI redisURI2 = RedisURI.Builder.redis("127.0.0.1").withPort(6379).withPassword("964988").build();
//      RedisURI redisURI3 = new RedisURI("127.0.0.1", 6379, Duration.ofMillis(-1));

        RedisClient redisClient = RedisClient.create(redisURI1);
        StatefulRedisConnection connection = redisClient.connect();

        /* RedisURI -> RedisClient -> RedisConnection -> RedisCommands*/

        // 同步
        RedisCommands<String, String> commands = connection.sync();
        System.out.println(commands.get("name"));

        // 异步
        RedisAsyncCommands<String, String> asyncCommands = connection.async();
        RedisFuture<String> rf = asyncCommands.get("wk");

//      rf.thenAccept(System.out::println);
        while (!rf.isDone()){
            System.out.println(rf.get());
        }

        connection.close();
        redisClient.shutdown();
    }
}
