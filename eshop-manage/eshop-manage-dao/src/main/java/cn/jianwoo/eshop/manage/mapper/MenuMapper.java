package cn.jianwoo.eshop.manage.mapper;

import cn.jianwoo.eshop.manage.entity.ItemProv;
import cn.jianwoo.eshop.manage.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuMapper {

    int insert(Menu menu);
    int update(Menu menu);
    int delete(Menu menu);
    Integer count();
    Integer max();
    List<Menu> getMenulist() ;
    List<Menu> getMenulistAndOn() ;
    Menu getMenuById(Integer id) ;
}