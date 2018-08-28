package com.faceyee.service.impl;

import com.faceyee.common.core.exception.method2.UserNotFoundExcetion;
import com.faceyee.common.security.model.UserModel;
import com.faceyee.domain.entity.User;
import com.faceyee.domain.repository.UserRepository;
import com.faceyee.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 97390 on 8/28/2018.
 */
@Service
public class TokenService {
    private final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /*当调用这个方法的时候，会从一个名叫 loginCache 的缓存中查询，如果没有，则执行实际的方法（即查询数据库），
    并将执行的结果存入缓存中，否则返回缓存中的对象。*/
    // 使用了一个缓存名叫 loginCache
    @Cacheable(value="userCache") // 属性条件condition="#uuid.length() <= 4"
    // 缓存中的 key 就是参数 name，value 就是 User 对象
    public User getUser(String name) {

        // 方法内部实现不考虑缓存逻辑，直接实现业务
        logger.info("real querying user... {}", name);
        Optional<User> userOptional = getFromDB(name);
        if (userOptional == null || !userOptional.isPresent()) {
            throw new UserNotFoundExcetion(String.format("can not find user by user name : [%s]", name));
        }

        return userOptional.get(); // 将执行的结果存入缓存中
    }

    private Optional<User> getFromDB(String name) {
        logger.info("real querying db... {}", name);
        User user = userRepository.findUserByUserName(name);
        if (user != null && user.getUserName() != null){
            user.setPassword("salted"); // 混淆密码信息
            return Optional.of(user); // fromNullable
        }
        return null;
    }

    @CacheEvict(value="userCache",key="#user.getUserName()")
    public void updateUser(User user) { // 更新某人
        updateDB(user);
    }

    private void updateDB(User user) {
        logger.info("real update db...{}", user.getUserName());
        userService.save(user);
    }

    @CacheEvict(value="userCache",allEntries=true)
    public void reload() { // 清空
    }


    /*上面是usercache的读，写，清空*/
    /*下面是登陆用户的session缓存*/


    // 使用了一个缓存名叫 loginCache
    @Cacheable(value="loginCache")
    // 缓存中的 key 就是参数 uuid，value 就是 UserModel 对象
    public UserModel getSessionUser(String uuid) { // 本方法是在判断用户是否已经登录时使用，参数是前台传进来的用户保存的sessionID

        logger.info("loginCache no this user... {}", uuid);

        return null;
    }

    @CachePut(value = "loginCache") // 方法执行后，加入缓存
    public UserModel createSessionToken(String name){ // 键不是uuid，会不会有影响？

        UserModel userModel = new UserModel();
        UUID UUID = java.util.UUID.fromString(name);
        userModel.setSessionUUID(UUID.toString());
        userModel.setUserName(name);
        return userModel;
    }
}
