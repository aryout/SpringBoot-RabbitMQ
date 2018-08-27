package com.faceyee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.faceyee") // 配置包根路径
// 在程序的入口中加入@ EnableCaching开启缓存技术：
@EnableCaching
/*在需要缓存的地方加入@Cacheable注解，比如在getByIsbn（）方法上加入@Cacheable(“books”)，这个方法就开启了缓存策略，
当缓存有这个数据的时候，会直接返回数据，不会等待去查询数据库。*/
public class SpringbootRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRabbitmqApplication.class, args);

	}
}
