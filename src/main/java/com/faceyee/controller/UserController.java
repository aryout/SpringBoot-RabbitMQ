package com.faceyee.controller;

import com.faceyee.domain.entity.User;
import com.faceyee.domain.repository.UserRepository;
import com.faceyee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 97390 on 8/21/2018.
 */
@Controller
@RequestMapping(value = "/user") // 类声明为一级URL
public class UserController {
    @Autowired // @Resource
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "/index")// 类声明为二级URL
    public String index(){
        return "/user/index";
    } // 方法名往往和该级URL对应

    @RequestMapping(value = "/show")  // http://localhost:8080/user/show?userName=
    @ResponseBody
    public String show(@RequestParam(value = "userName") String userName){
        User user = userService.findByUserName(userName);

        if (user != null){
            return user.getId()+"/"+user.getUserName()+"/"+user.getPassWord();
        }else return "null";
    }

    @RequestMapping("/getUser")
    @Cacheable(value="user-key")
    public User getUser() {
        User user=userService.findByUserName("aa");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }

//    @RequestMapping("/getUsers")
//    @Cacheable(value="key-Users")
//    public List<User> getUsers() {
//        List<User> users=userService.findAll();
//        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
//        return users;
//    }

    @RequestMapping(value = "/list")
    public PageInfo<User> findUserList(int pageNum, int pageSize) {
        PageInfo<User> pageInfo = userService.findAllUserList(pageNum, pageSize);
        return pageInfo;
    }

//    @RequestMapping(value = "/list2")
//    public Page<User> findUserList2(int pageNum, int pageSize) {
          // // 分页查询第pageNum页用户，限制每页pageSize条记录，按创建时间降序排序
//        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("gmtCreate")));
            // 同时要求用户的id大于2
            Specification<User> specification = (root, query, builder) -> {
                /*
                    root：实体对象引用，这里是user
                    query：规则查询对象
                    builder：规则构建对象
                 */
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(builder.greaterThan(root.get("id").as(Integer.class), 2));
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            };
//        Page<User> userPage = userRepository.findAll(pageRequest);
//        return userPage;
//    }

    //@RequestMapping(value = "/save")
    @PostMapping(value = "/save")
    public long save(@RequestBody @Validated User user, Errors errors) {// @Validated注解当没使用Errors 类型的参数声明,Spring MVC 框架会抛出 MethodArgumentNotValidException 异常
        return userService.save(user);
    }
}
