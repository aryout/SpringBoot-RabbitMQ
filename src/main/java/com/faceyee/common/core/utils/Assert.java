package com.faceyee.common.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by 97390 on 8/28/2018.
 */
public class Assert {
    public static void isBlank(String text, String message){
        org.springframework.util.Assert.state(StringUtils.isBlank(text), message);
    }
}
