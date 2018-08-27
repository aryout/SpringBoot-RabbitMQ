package com.faceyee.service.impl;

import com.faceyee.domain.repository.UserRepository;
import com.faceyee.domain.entity.User;
import com.faceyee.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by 97390 on 8/21/2018.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public User findByUserName(String userName){ // service 的业务方法名往往和Dao的方法名匹配； 当然，复杂的业务方法会集合操作多个Dao方法，所以，事务是基于service的
        User user = null;
        try {
            user = userRepository.findByUserName(userName);
        }catch (Exception ignored){}

        return user;
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public PageInfo<User> findAllUserList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userRepository.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        // 具体使用
        redisTemplate.opsForList().leftPush("user:list", JSON.toJSONString(list));
        stringRedisTemplate.opsForValue().set("user:name", "张三");
        return pageInfo;
    }

    /**
     * 新增
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public long save(User user) {
        userRepository.save(user);
        return userRepository.findByUserName(user.getUserName()).getId();
    }

}
