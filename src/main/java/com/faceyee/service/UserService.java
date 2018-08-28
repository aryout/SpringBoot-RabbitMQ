package com.faceyee.service;

import com.faceyee.domain.entity.User;

/**
 * Created by 97390 on 8/26/2018.
 */
public interface UserService {

    public String login(String userName, String pwd);
    public String signup(String userName, String pwd);

    public User getUserByUserName(String userName);
    public User getUserByUserId(String uuid);

    public String save(User user);

    public void  addTel(String tel, User user);
    public void  addMail(String mail, User user);
}
