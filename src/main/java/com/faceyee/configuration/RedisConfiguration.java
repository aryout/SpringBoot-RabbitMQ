package com.faceyee.configuration;

/**
 * Created by 97390 on 8/22/2018.
 */

import com.faceyee.utils.common.RedisUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Redis 配置类
 *
 */
@Configuration
@EnableCaching
@PropertySource("classpath:config/redis.properties")
public class RedisConfiguration extends CachingConfigurerSupport {

    /**
     * Logger
     */
    protected static final Logger lg = LoggerFactory.getLogger(RedisConfiguration.class);


    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;


    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        //  设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
        // 使用：进行分割，可以很多显示出层级关系
        // 这里其实就是new了一个KeyGenerator对象，只是这是lambda表达式的写法，我感觉很好用，大家感兴趣可以去了解下
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(":").append(String.valueOf(obj));
            }
            String rsToUse = String.valueOf(sb);
            lg.info("自动生成Redis Key -> [{}]", rsToUse);
            return rsToUse;
        };
    }

//    @Bean // 静态方法create使用Spring提供的默认配置
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisCacheManager cacheManager = RedisCacheManager.create(redisConnectionFactory);
//        return cacheManager;
//    }

    @SuppressWarnings("rawtypes")
    @Bean
    @Override
    public CacheManager cacheManager() {
        lg.info("初始化 -> [{}]", "CacheManager RedisCacheManager Start");
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();  // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        // config设置时必须有返回重新赋值,否则不起作用,因为RedisCacheConfiguration的属性都是final修饰的,每次修改都会重新new一个新对象返回
        config = config.entryTtl(Duration.ofMinutes(1))     // 设置缓存的默认过期时间，也是使用Duration设置
                       .disableCachingNullValues();     // 不缓存空值

        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames =  new HashSet<>();
        cacheNames.add("my-redis-cache1");
        cacheNames.add("my-redis-cache2");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("my-redis-cache1", config);
        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));

        RedisCacheManager cacheManager = RedisCacheManager.builder(jedisConnectionFactory)     // 使用自定义的缓存配置初始化一个cacheManager
                                                          .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                                                          .withInitialCacheConfigurations(configMap)
                                                          .build();
        return cacheManager;
    }

    @Bean(name = "redisTemplate") // spring 和 jedis 对话的 RedisTemplate
    public RedisTemplate<String, Object> redisTemplate( ) {
        //设置序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory); // 自己实现redisTemplate,就必须传入jedisConnectionFactory bean,那么就必须自己实现jedisConnectionFactory而不能自动配置

        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer); // key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer); // value序列化
        redisTemplate.setHashKeySerializer(stringSerializer); // Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer); // Hash value序列化
        //redisTemplate.afterPropertiesSet(); // 内部会调用的,不用在外面显式调用.RedisConnectionFactory is required

        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Override
    @Bean
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        lg.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                lg.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                lg.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key)    {
                lg.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                lg.error("Redis occur handleCacheClearError：", e);
            }
        };
    }

    @Bean(name = "redisUtil") // 注入封装RedisTemplate
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }

    @Bean
    public String test(){
        // 在这个bean中,调用了注入好的jedisConnectionFactory,可以看到,单机信息和池信息都能返回配置文件中的值
        System.out.println("after_JedisConnectionFactory_DI)test: "+ jedisConnectionFactory.getHostName());
        System.out.println("after_JedisConnectionFactory_DI)test: "+ jedisConnectionFactory.getPassword());
        System.out.println("after_JedisConnectionFactory_DI)test: "+ jedisConnectionFactory.getDatabase());
        System.out.println("after_JedisConnectionFactory_DI)test: : "+ jedisConnectionFactory.getUsePool());
        System.out.println("after_JedisConnectionFactory_DI)test: : "+ jedisConnectionFactory.getPoolConfig().getMaxIdle());
        return "nu";
    }

    @Bean() // 如果方法名不和bean名相等,会找不到,需要在bean注解这添加名字
    @ConfigurationProperties(prefix="spring.redis") // 把poolconfig也包含在里面,所以这里也自动注入了jedisPoolConfig bean, 可以不再如上方法单独配置它
    public JedisConnectionFactory jedisConnectionFactory(){
        //System.out.println("(after_JedisPoolConfig_DI)jedisPoolConfig: "+ jedisPoolConfig.getMaxIdle()); // jedisPoolConfig这个bean是已经注入返回的bean,值是配置文件中注入的

//            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        // 要么使用redisStandaloneConfiguration,要么使用jedisPoolConfig,想两者都用,只能使用jedisPoolConfig本地构造
        // DefaultJedisClientConfiguration对象,再和redisStandaloneConfiguration一起传入工厂构造器.但是报一个错.

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();

        System.out.println("(during_JedisConnectionFactory_DI)jedisPoolConfig: "+ jedisConnectionFactory.getPoolConfig().getMaxIdle());
        // 虽然jedisConnectionFactory构造过程中需要jedisPoolConfig,并且jedisPoolConfig已经在之前注入成功,但是在jedisConnectionFactory这个bean
        // 的构造返回之前(本方法体还未结束),读取它的成员jedisPoolConfig信息,都是默认的未被注入的值


        lg.info("JedisPool init successful，host -> [{}]；port -> [{}]", jedisConnectionFactory.getHostName(), jedisConnectionFactory.getPort());
        lg.info("Create JedisConnectionFactory successful");
        return jedisConnectionFactory;
    }

/*    @Configuration
//    @EnableAutoConfiguration(exclude = JedisConnectionFactory.class)
    //@Data 自定义实体类时,需要配置get/set,但是这里是注入第三方包里的类,它的实现显然已经有get/set了,这里无需再加
//    @PropertySource("classpath:application.properties")
    @PropertySource("classpath:config/redis.yml") // 虽然设置了这个,但还是读取的默认配置文件?
    static class RedisConfig {

//        @Autowired
//        JedisPoolConfig jedisPoolConfig;
//
//
//        @Bean // 其实,在这里只是单纯的注入参数值,生成bean,我们知道,spring为我们的常用配置自动扫描生成bean(只要配置文件按照约定格式,而且不能在默认配置文件外,否则只能自己像这里一样手动创造配置类)
//        // 包括JedisConnectionFactory,我们都可以不用再显式配置,jedisConnectionFactory被RedisTemplate包装好了,我们使用的是RedisTemplate来操作,如外部类那样.
//        @ConfigurationProperties(prefix="spring.redis.poolconfig") // poolconfig在配置文件可以是驼峰,在这里得全是小写
//        public JedisPoolConfig jedisPoolConfig(){
//            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//            System.out.println("(during_JedisPoolConfig_DI)jedisPoolConfig: "+ jedisPoolConfig.getMaxIdle()); // 还没有return回bean时,检验值都是未注入的,检验没有意义
//            return jedisPoolConfig;
//        }

    }*/
}