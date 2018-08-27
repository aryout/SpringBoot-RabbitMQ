package com.faceyee.controller.api;

/**
 * Created by 97390 on 8/24/2018.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * <p>Title:APILoginSignController </p>
 * <p>Description: APP登录接口</p>
 */
@RestController(value="api.APILoginSignController") // 包名+类名
// value: 该值可以指示对逻辑组件名称的建议，在自动检测的组件的情况下将其转换为Spring bean。
@RequestMapping("/api")
public class APILoginSignController {

    /**
     * APP验签登录
     * @param dealerResponse
     * @return
     */
//    @PostMapping("/login_sign")
}