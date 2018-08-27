package com.faceyee.domain.repository.dao;

import com.faceyee.utils.condition.MySqlDbTypeCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 97390 on 8/24/2018.
 */

@Repository
@Conditional(MySqlDbTypeCondition.class)
public class JdbcUserDaoImpl implements UserDao {
    @Override
    public List<String> getAllUserNames() {
        return Arrays.asList("jdbc", "test");
    }
}