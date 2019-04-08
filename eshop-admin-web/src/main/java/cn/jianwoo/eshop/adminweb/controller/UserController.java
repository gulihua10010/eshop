package cn.jianwoo.eshop.adminweb.controller;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.manage.entity.User;
import cn.jianwoo.eshop.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
UserService userService;
    @GetMapping("/getuserlist")
    public LayuiResult getuserlist(Integer page, Integer limit,String kw){
        System.out.println(page);
        System.out.println(limit);
        System.out.println(kw);
        return userService.getList(page,limit,kw);
    }
    @GetMapping("/user/updatestatus")
    public EShopResult updatestatus(Long id, Integer status){
        User user=new User();
        user.setId(id);
        user.setStatus(status);
        System.out.println(user);
        EShopResult eShopResult=userService.updateUser(user);
        return  eShopResult;
    }
    @PostMapping("/user/updateStatusSelectUser")
    public EShopResult updateStatusSelectUser(@RequestBody List<User> users ){

            for (User i :users ) {
                User user=new User();
                user.setId(i.getId());
                user.setStatus(-1);
                userService.updateUser(user);
            }

        return  EShopResult.ok();
    }

    @PostMapping("/user/adduser")
    public EShopResult adduser(@RequestBody User  users ){
        EShopResult result=userService.register(users);
        System.out.println(users);
        System.out.println(result);
        return  result;
    }
}
