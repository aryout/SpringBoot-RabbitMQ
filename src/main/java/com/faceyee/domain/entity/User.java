package com.faceyee.domain.entity;


import com.faceyee.common.core.annotations.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.node.NullNode;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*; // Entity.Id.GeneratedValue
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Created by 97390 on 8/21/2018.
 */
@Data
@Entity
@Table(name = "tbl_user_rabbitmq")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(name="user_id", insertable = true,updatable = false,length = 12, nullable = false, unique = true) // varchar(36)
    private String userId; // UUIDd的字符串需要去除'-'

    @Column(name="user_name",nullable = false, unique = true, length=512)
    private String userName;

    @Max(value = 999999,message = "超过最大数值")
    @Min(value = 0,message = "密码设定不正确")
    @JsonIgnore // Rest接口返回用户信息时,屏蔽密码,但不是不序列化到数据库,同时接受Rest登录请求时传入的密码使用的是UserModel对象,在那里不是JsonIgnore的,可以接受到
    private String password;


    @Phone
    @Column(nullable = true, unique = true)
    private String tel;

    @Email
    @Column(nullable = true, unique = true)
    private String email;


    public User(){
        super();
    }
    public User(String userName, String password) {
        super();
        this.password = password;
        this.userName = userName;
    }
}
