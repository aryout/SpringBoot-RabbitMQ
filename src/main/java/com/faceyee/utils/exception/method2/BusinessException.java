package com.faceyee.utils.exception.method2;

/**
 * Created by 97390 on 8/23/2018.
 */
public class BusinessException extends DescribeException { // 交易事务失败

    private static final long serialVersionUID = -2052344358836723453L;

    public BusinessException(String message){
        super(message);
    }
}