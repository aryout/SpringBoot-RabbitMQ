package com.faceyee.utils.common;

/**
 * Created by 97390 on 8/27/2018.
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNumeric(String str){
        try {
            if (str != null && StringUtil.isNumeric(str)) {
                Integer va = Integer.parseInt(str);
            }
        }catch (Exception ex){
            return false;
        }
        return true;
    }
}