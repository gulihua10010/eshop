package cn.jianwoo.eshop.webconfig.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.WebConfig;

import java.util.List;

public interface WebConfigService {
    EShopResult insert(WebConfig webConfig);
    EShopResult update(WebConfig webConfig);
    EShopResult deleteAll();
    List<WebConfig> geWebConfiglist() ;
    List<WebConfig> geWebConfiglistAndOn() ;
    WebConfig getWebConfigById(Integer id) ;
}
