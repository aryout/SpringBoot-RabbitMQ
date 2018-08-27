package com.faceyee.utils.exception.method2;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;


/**
 * Created by 97390 on 8/24/2018.
 */
@Configuration
public class ErrorPageFactory {

        /*两种都可以*/
/*    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/unauthenticated"));
        factory.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));// path 是转发到的URL,不是本地路径,所以还得在控制器里为这个path翻译到本地路径
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
        factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
        return factory;
    }*/

    @Bean // 非spring mvc映射错误文件,对于没有使用spring mvc的应用，可以通过实现接口 ErrorPageRegistrar 指向错误页面
    public ErrorPageRegistrar errorPageRegistrar(){
        return new ErrorPageRegistrar() {
            @Override
            public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
                errorPageRegistry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400")); // path 是转发到的URL,不是本地路径,所以还得在控制器里为这个path翻译到本地路径
                errorPageRegistry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
                errorPageRegistry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
            }
        };
    }
}