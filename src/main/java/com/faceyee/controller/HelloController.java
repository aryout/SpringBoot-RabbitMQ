package com.faceyee.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by 97390 on 8/21/2018.
 */
@RestController // 下面的方法的返回值直接输出到当前页面,没有作为跳转链接
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Locale locale, Model model) {
        return "helloDemo world";
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
