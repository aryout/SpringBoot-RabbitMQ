package com.faceyee.controller.api;

import com.faceyee.common.core.annotations.IgnoreAuth;
import com.faceyee.common.core.exception.method2.SignupExcetion;
import com.faceyee.domain.entity.CaptchaEntity;
import com.faceyee.common.security.model.UserModel;
import com.faceyee.service.CaptchaService;
import com.faceyee.service.impl.TokenService;
import com.faceyee.service.UserService;
import com.faceyee.common.core.utils.Assert;
import com.faceyee.common.core.exception.method2.LoginExcetion;
import com.faceyee.common.core.result.RestServiceModel;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by 97390 on 8/23/2018.
 */
@RestController
public class IndexController {

    @Autowired
    CaptchaService captchaService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    Producer producer;

    @IgnoreAuth
    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @IgnoreAuth
    @ResponseBody
    @RequestMapping(value = "/captcha", method = RequestMethod.POST)
    public Map<String, Object> captcha(HttpServletResponse response) throws ServletException, IOException {

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        // 生成captcha的token
        Map<String, Object> map = captchaService.createToken(text);
        map.put("img", encoder.encode(outputStream.toByteArray()));

        return map; // UUID，过期时间，img三个键，不直接传输文字到前台
    }

    /**
     * 登录
     */
    @IgnoreAuth
    @PostMapping("/login")
    @ApiOperation(value = "登录",notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType="string", name = "account", value = "账号", required = true),
            @ApiImplicitParam(paramType = "query", dataType="string", name = "password", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType="string", name = "captcha", value = "验证码", required = true)
    })
    public RestServiceModel login(HttpServletRequest request, String userName, String password, String captcha, String ctoken){
        Assert.isBlank(userName, "账号不能为空");
        Assert.isBlank(password, "密码不能为空");
        Assert.isBlank(captcha, "验证码不能为空");
        CaptchaEntity captchaEntity  = captchaService.queryByToken(ctoken); // 通过传过去的ctoken（UUID，不是session的UUID）找出本地存储的验证码

        if(!captcha.equalsIgnoreCase(captchaEntity.getCaptcha())){ // 对方解出来的验证码和本地验证码比对
            throw new LoginExcetion("验证码不正确");
            //return R.error("验证码不正确");
        }

        //用户登录
        userService.login(userName, password);

        //生成token,这里生成的是缓存用于session等跨域技术的，不是验证码的
        UserModel userModel = tokenService.createSessionToken(userName); // 应该返回登陆用户的session UUID，缓存过期时间，用户名等

        return new RestServiceModel(true, 0, "login success", userModel);
    }


    /**
     * 注册
     */
    @IgnoreAuth
    @PostMapping("/signup")
    @ApiOperation(value = "注册",notes = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType="string", name = "account", value = "账号", required = true),
            @ApiImplicitParam(paramType = "query", dataType="string", name = "password", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType="string", name = "captcha", value = "验证码", required = true)
    })
    public RestServiceModel signup(HttpServletRequest request, String userName, String password, String captcha, String ctoken){
        Assert.isBlank(userName, "账号不能为空");
        Assert.isBlank(password, "密码不能为空");
        Assert.isBlank(captcha, "验证码不能为空");
        CaptchaEntity captchaEntity  = captchaService.queryByToken(ctoken); // 通过传过去的ctoken（UUID，不是session的UUID）找出本地存储的验证码

        if(!captcha.equalsIgnoreCase(captchaEntity.getCaptcha())){ // 对方解出来的验证码和本地验证码比对
            throw new SignupExcetion("验证码不正确");
            //return R.error("验证码不正确");
        }

        //用户注册
        userService.signup(userName, password);

        //生成token,这里生成的是缓存用于session等跨域技术的，不是验证码的
        UserModel userModel = tokenService.createSessionToken(userName); // 应该返回注册用户的session UUID，缓存过期时间，用户名等

        return new RestServiceModel(true, 0, "signup success", userModel);
    }
}
