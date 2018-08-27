package com.faceyee.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by 97390 on 8/24/2018.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override protected void configure(HttpSecurity http) throws Exception {
        //指定路径需要登录权限，对静态资源放行，无需授权即可访问
        http.authorizeRequests().antMatchers("/css/**", "/js/**", "/images/**",
                //请求路径包含order的，都需要去登陆
                "/login").permitAll().antMatchers("/order/**").hasRole(("USER")).and().
                    formLogin();//在该处追加.loginPage("/tologin")去自定义的登录界面(当然还需要写一个控制器);
        http.csrf().disable();
        //单点登录。如果已经登录，其它登录会使当前登录session失效。进一步操作则需要再次登录
        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
        //		BCryptPasswordEncoder 加密类
        //如果登出，使session无效
        http.logout().invalidateHttpSession(true);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()//全局默认用户
            .withUser("admin").password("admin").roles("USER");
    }
}
