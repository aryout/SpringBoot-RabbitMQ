package com.faceyee.utils.exception.method2;

import com.faceyee.utils.restful.RestServiceModel;
import lombok.Data;

/**
 * Created by 97390 on 8/23/2018.
 */
/*
* 所有自定义异常都需要继承这个类
* 继承exception，加入错误状态值
* */
@Data
public class DescribeException extends RuntimeException{

    private String code;
    private String massage;

    /**
     * 自定义错误信息
     * @param message
     */
    // 运行时,自定义抛出动态构造的excetion
    public DescribeException(String message) {
        super(message);
        this.massage = message;
    }
}