package com.faceyee.utils.restful;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by 97390 on 8/23/2018.
 */
@Data // 不加这个的话,会抛出HttpMessageNotWritableException[要有getter方法]
public class RestServiceModel { // 封装响应报文
    private int code; // 状态码
    private boolean status = true; // 状态
    private String msg; // 描述
    //    @JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
    @JsonInclude(JsonInclude.Include.NON_NULL) // 不加这个的话,会抛出HttpMessageNotWritableException[要排除null]
    private Object data; // 数据


    public RestServiceModel(){}


    // 自定义
    public RestServiceModel(boolean status, int code, String msg, Object data){
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "\"status\":" + status +
                ", \"code\":" + code +
                ", \"msg\":" + msg  +
                ", \"data\":" + data +
                "}";
    }
}
