package com.faceyee.domain.entity;

import com.faceyee.utils.validator.Phone;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

/**
 * Created by 97390 on 8/23/2018.
 */
@Data
public class UserModel { // 只为登录时接受参数使用 请选择用户名或手机号登录
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @Phone
    private String tel;

    @NotBlank(message = "密码不能为空")
    private String passWord;
}
