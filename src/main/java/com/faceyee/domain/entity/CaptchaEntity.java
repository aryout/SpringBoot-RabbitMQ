package com.faceyee.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 97390 on 8/28/2018.
 */
@Data
@AllArgsConstructor
@Entity
// TODO： 修改为缓存形式，不使用数据库
public class CaptchaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false, name = "id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    private String captcha; // 验证码 可以重复使用

    private String token; // UUID 不可以重复，更新验证码的当前生成UUID

    private Date updateTime;

    private Date expireTime;

    public CaptchaEntity(){
        super();
    }
}
