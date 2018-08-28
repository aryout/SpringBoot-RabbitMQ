package com.faceyee.common.security.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by 97390 on 8/23/2018.
 */
@Data
public class UserModel {
    @NotBlank(message = "用户名不能为空")
    private String userName;


    @NotBlank(message = "UUID不能为空")
    private String sessionUUID;
}
