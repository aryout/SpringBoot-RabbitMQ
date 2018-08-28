package com.faceyee.common.core.annotations;

import java.lang.annotation.*;

/**
 * Created by 97390 on 8/28/2018.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {

    String message() default "本访问不需要用户登录权限";
}
