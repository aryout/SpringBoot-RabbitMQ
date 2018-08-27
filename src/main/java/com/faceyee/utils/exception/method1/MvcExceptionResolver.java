package com.faceyee.utils.exception.method1;

/**
 * Created by 97390 on 8/26/2018.
 */

import com.alibaba.fastjson.JSON;
import com.faceyee.utils.exception.method2.DescribeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * mvc异常处理器
 *
 */
@Component
public class MvcExceptionResolver  implements HandlerExceptionResolver {
    private final static Logger LOGGER = LoggerFactory.getLogger(MvcExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            String errorMsg="";
            boolean isAjax= "1".equals(request.getParameter("isAjax"));

            //ex 为业务层抛出的自定义异常
            if(ex instanceof DescribeException){

                /*others...*/
                errorMsg=ex.getMessage();
                LOGGER.error(errorMsg);
            }else{
                //ex为非自定义异常，则
                errorMsg=ex.getMessage();
                LOGGER.error(errorMsg);

            }

            if(isAjax){ // json
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(AjaxUtil.messageMap(500, errorMsg)));
                return new ModelAndView();
            }else{ // page
                //否则，  输出错误信息到自定义的500.jsp页面
                ModelAndView mv = new ModelAndView("errorPage/500.jsp");
                mv.addObject("errorMsg", errorMsg);
                return mv ;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return new   ModelAndView();

    }

}