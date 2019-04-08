package cn.jianwoo.eshop.webconfig.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Homeppt;

import java.util.List;

public interface HomepptService {

    EShopResult insert(Homeppt homeppt);
    EShopResult update(Homeppt homeppt);
    EShopResult deleteAll();
    List<Homeppt> getHomepptlist() ;
    List<Homeppt> getHomepptlistAndOn() ;
    Homeppt getHomepptById(Integer id) ;
}
