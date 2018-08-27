package com.faceyee.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 97390 on 8/24/2018.
 */
@Controller
public class ErrorPageController {

    @RequestMapping(value = "/400")
    ModelAndView validation(){
        return new ModelAndView("errorPage/400");
    }

    @RequestMapping(value = "/404")
    ModelAndView notFound(){
        return new ModelAndView("errorPage/404");
    }

    @RequestMapping(value = "/500")
    ModelAndView serverError(){
        return new ModelAndView("errorPage/500");
    }
}
