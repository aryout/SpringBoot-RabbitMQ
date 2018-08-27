package com.faceyee.domain.repository;

import com.faceyee.Springbootdemo1Application;
import com.faceyee.domain.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by 97390 on 8/21/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositotyTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUserName() throws Exception {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        String formattedDate = dateFormat.format(new Date()); // Date 是用util包的?

        userRepository.save(new User("user1", "123","aa@126.com"));

        Assert.assertEquals("user1",userRepository.findByUserName("user1"));

    }

    @Test
    public void findUserByName() throws Exception {
        Assert.assertEquals("user1",userRepository.findUserByUserName("user1"));
    }

    @Test
    public void findUserByNameOrEmail() throws Exception {
        Assert.assertEquals("123", userRepository.findUserByUserNameOrEmail("user1", "aa@126.com").getPassWord());
    }

    @After
    public void delect() throws Exception{
        userRepository.delete(userRepository.findByUserName("user1"));
    }

}