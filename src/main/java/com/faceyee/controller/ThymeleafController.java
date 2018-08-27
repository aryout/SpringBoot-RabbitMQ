package com.faceyee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 97390 on 8/21/2018.
 */
@Controller // 下面方法的返回值作为跳转链接,返回了hello.html,当然,解析了这个文件,但是url没有变化为hello,除非返回值中指明redirect:
public class ThymeleafController {
    @RequestMapping("/hi")
    public String hello(Locale locale, Model model) {
        model.addAttribute("greeting", "Hello!");

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        String formattedDate = dateFormat.format(date);
        model.addAttribute("currentTime", formattedDate);

        return "redirect:helloDemo";
    }
}
