package com.faceyee.common.core.utils;

import java.util.UUID;

/**
 * Created by 97390 on 8/28/2018.
 */
public class UUIDUtil {
    public static String buil(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String buil(String from){
        return UUID.fromString(from).toString().replaceAll("-", "");
    }
}
