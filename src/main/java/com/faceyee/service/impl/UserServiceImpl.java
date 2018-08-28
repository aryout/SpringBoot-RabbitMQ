package com.faceyee.service.impl;

import com.faceyee.common.core.exception.method2.UserNotFoundExcetion;
import com.faceyee.common.core.utils.UUIDUtil;
import com.faceyee.domain.entity.User;
import com.faceyee.domain.repository.UserRepository;
import com.faceyee.common.core.exception.method2.LoginExcetion;
import com.faceyee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by 97390 on 8/28/2018.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public String login(String userName, String pwd) {
        User user = userRepository.findUserByUserName(userName);
        if ( user == null  || user.getUserName() != null){
            throw new LoginExcetion("该用户没有注册");
        }else if (!user.getPassword().equals(pwd)){
            throw new LoginExcetion("用户密码错误");
        }
        return userName;
    }

    @Override
    public String signup(String userName, String pwd) {
        User user = userRepository.findUserByUserName(userName);
        if ( user != null && user.getUserName() != null){
            throw new LoginExcetion("该用户已经注册");
        }
        user = new User(userName, pwd);
        user.setUserId(UUIDUtil.buil());
        save(user);
        return userName;
    }

    @Override
    public String save(User user) {
        userRepository.saveAndFlush(user);
        return user.getUserName();
    }

    @Override
    public User getUserByUserName(String userName) {
        User user = userRepository.findUserByUserName(userName);
        if ( user != null && user.getUserName() != null){
            throw new UserNotFoundExcetion("该用户没有注册,找不到该用户");
        }
        return user;
    }

    @Override
    public User getUserByUserId(String uuid) {
        User user = userRepository.findUserByUserId(uuid);
        if ( user != null && user.getUserName() != null){
            throw new UserNotFoundExcetion("该用户没有注册,找不到该用户");
        }
        return user;
    }

    @Override
    public void addTel(String tel, User user){
        user.setTel(tel);
        userRepository.saveAndFlush(user);
    }
    @Override
    public void addMail(String mail, User user){
        user.setEmail(mail);
        userRepository.saveAndFlush(user);
    }
}
