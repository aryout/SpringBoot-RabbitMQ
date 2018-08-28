package com.faceyee.common.core.interceptor;

import com.faceyee.common.core.annotations.IgnoreAuth;
import com.faceyee.common.core.exception.method2.UnAuthExcetion;
import com.faceyee.common.core.result.RestServiceModel;
import com.faceyee.common.security.model.UserModel;
import com.faceyee.service.impl.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by 97390 on 8/28/2018.
 */
@Component
@Order(1)
@Aspect // 基于AOP的拦截器，不用实现HandlerInterceptor
public class LoginInterceptor {

    @Autowired
    TokenService tokenService;


    /*
    * 登录session验证

　　防止浏览器端绕过登录，直接进入到应用

　　或者session超时后，返回到登录页面
    * */
    Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Around("@within(org.springframework.web.bind.annotation.RestController)") // 指定PointCut
    // 通知的范围是只要有类添加了@RestController的注解，那么类中的方法，只要被调用，都会执行相应的通知
    // 如果用户没有登录，那么请求就会被打回，并在页面上给与用户提示
    public void loginCheck(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uuid = (String) request.getSession().getAttribute("uuid"); // uuid 是session 中定义的关键字，也可能叫token，由开发者指定


        final MethodSignature methodSignature = (MethodSignature) pjp.getSignature(); // 获取连接点的方法签名对象；
        final Method method = methodSignature.getMethod();
        boolean ignoreAuth = method.isAnnotationPresent(IgnoreAuth.class); // 该方法上有某注解

        if ((null == uuid) && !ignoreAuth){ // 进一步，检查uuid格式是否正确，再查询缓存
            throw  new UnAuthExcetion("用户未登录，无法访问");
            // 或者，return new ModelAndView("user/login")返回登录页面
        }
        UserModel loginUser = tokenService.getSessionUser(uuid);


        if (loginUser == null && !ignoreAuth) {
            throw  new UnAuthExcetion("用户未登录，无法访问");
        }
        pjp.proceed(); // 通过反射执行目标对象的连接点处的方法
    }
}