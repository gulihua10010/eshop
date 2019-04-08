package cn.jianwoo.eshop.manage.mapper;

import cn.jianwoo.eshop.manage.entity.Menu;
import cn.jianwoo.eshop.manage.entity.WebConfig;

import java.util.List;

public interface WebConfigMapper {

    int insert(WebConfig webConfig);
    int update(WebConfig webConfig);
    int delete(WebConfig webConfig);
    int deleteAll();

    List<WebConfig> geWebConfiglist() ;
    List<WebConfig> geWebConfiglistAndOn() ;
    WebConfig getWebConfigById(Integer id) ;
}