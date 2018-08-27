package com.faceyee.domain.repository.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 97390 on 8/24/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;


    @BeforeClass
    public static void init() {
        System.setProperty("dbType", "mongo");
        //System.setProperty("dbType", "mysql");

    }

    @AfterClass
    public static void close() {
        System.clearProperty("dbType");
    }

    @Test
    public void testMysqlDbType() {
        System.out.println("=====================");
        final List<String> allUserNames = userDao.getAllUserNames();
        Assert.assertEquals(Arrays.asList(" Mongo db ", "test"), allUserNames);

        System.out.println("=====================");

    }
}