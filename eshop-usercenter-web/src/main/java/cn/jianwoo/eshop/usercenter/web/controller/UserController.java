package cn.jianwoo.eshop.usercenter.web.controller;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.User;
import cn.jianwoo.eshop.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/saveuser")
    public EShopResult saveuser(@RequestBody User user){
        System.out.println(user);
        return   userService.updateUser(user);
    }
}
