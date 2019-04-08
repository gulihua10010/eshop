package cn.jianwoo.eshop.adminweb.controller;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Menu;
import cn.jianwoo.eshop.manage.entity.WebConfig;
import cn.jianwoo.eshop.webconfig.api.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebconfigController {
    @Autowired
    WebConfigService webConfigService;
    @RequestMapping("/updatewebconfig")
    public EShopResult updatewebconfig(@RequestBody List<WebConfig> data) {
        System.out.println(data);
        webConfigService.deleteAll();
        for (WebConfig w:data  ) {
            webConfigService.insert(w);
        }
        return  EShopResult.ok();
    }
}
