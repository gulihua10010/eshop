package cn.jianwoo.eshop.adminweb.controller;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Homeppt;
import cn.jianwoo.eshop.webconfig.api.HomepptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomepptController {
    @Autowired
    HomepptService homepptService;

    @RequestMapping("/updatehomeppt")
    public EShopResult updatehomeppt(@RequestBody List<Homeppt> homeppts) {
        System.out.println(homeppts);
        homepptService.deleteAll();
        for (Homeppt h:homeppts   ) {
        homepptService.insert(h);
        }
        return  EShopResult.ok();
    }
}
