package com.faceyee;

import com.faceyee.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitmqApplicationTests {
/*	@Autowired
	User user;*/ // 实体不是bean，无法自动注入

	@Test
	public void contextLoads() {
	}

}
