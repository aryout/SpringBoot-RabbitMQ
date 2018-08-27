package com.faceyee.service;

import com.faceyee.domain.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * Created by 97390 on 8/26/2018.
 */
public interface UserService {

    public PageInfo<User> findAllUserList(int pageNum, int pageSize);

    public User findByUserName(String userName);

    public long save(User user);
}
