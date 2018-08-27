package com.faceyee.utils.restful;

import lombok.Data;

/**
 * Created by 97390 on 8/24/2018.
 */
@Data
public class RestServiceError {

    private String code;
    private String message;

    // 动态定义
    public RestServiceError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // 通过enum定义
    public static RestServiceError build (Type errorType, String message) { // message 是通过国际化工具从国际化文件里通过code读取message覆盖enum中定义的message的
        return new RestServiceError(errorType.getCode(), message);
    }

    public enum Type {
        BAD_REQUEST_ERROR("400", "Bad request error"),
        INTERNAL_SERVER_ERROR("500", "Unexpected server error"),
        VALIDATION_ERROR("444", "Found validation issues"),
        BUSSINESSERROR("-1", "bussiness error");

        private String code;
        private String message;

        Type(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}

