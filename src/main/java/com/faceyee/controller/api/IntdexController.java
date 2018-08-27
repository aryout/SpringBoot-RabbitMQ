package com.faceyee.controller.api;

import com.faceyee.domain.entity.User;
import com.faceyee.domain.entity.UserModel;
import com.faceyee.utils.restful.RestServiceModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by 97390 on 8/23/2018.
 */
@RestController
public class IntdexController {
    @RequestMapping("/login")
    // 直接在映射方法里面使用实体进行参数接收,这样,请求报文也是Json格式的(@RequestBody修饰)
    public RestServiceModel login(@RequestBody @Valid UserModel loginUser) { //  @Valid 参数校验
        // 请求数据接收后,说明请求成功,返回success
        // 至于登陆成功与否,是另一回事
        User user = new User();
        System.out.println(loginUser);
        System.out.println(loginUser.getUserName());

        user.setUserName(loginUser.getUserName());
        user.setPassWord(loginUser.getPassWord());
        return new RestServiceModel(true,0,"login success", user);
        // 这种是接口,返回json数据,如果想登录成功后展示具体的html页面,就不要用Rest
    }

    @RequestMapping("/signup")
    public RestServiceModel submit(@RequestBody @Valid UserModel signupUser) { //  @Valid 参数校验
        User user = new User();
        user.setUserName(signupUser.getUserName());
        user.setPassWord(signupUser.getPassWord());
        return new RestServiceModel(true,0,"sighup success", user);
    }
}
