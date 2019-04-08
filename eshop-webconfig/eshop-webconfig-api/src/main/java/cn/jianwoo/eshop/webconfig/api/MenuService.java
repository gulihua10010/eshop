package cn.jianwoo.eshop.webconfig.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Menu;

import java.util.List;

public interface MenuService {
    EShopResult insert(Menu menu);
    EShopResult update(Menu menu);
    EShopResult delete(Menu menu);
    Integer count();
    List<Menu> getMenulist() ;
    List<Menu> getMenulistAndOn() ;
    Menu getMenuById(Integer id) ;
}
