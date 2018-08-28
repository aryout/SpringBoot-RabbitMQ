package com.faceyee.domain.repository;

import com.faceyee.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 97390 on 8/21/2018.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{ // 两个泛型参数分别代表Java POJO类以及主键数据类型

    User findUserByUserName(String userName);
    User findUserByUserId(String uuid);
}
