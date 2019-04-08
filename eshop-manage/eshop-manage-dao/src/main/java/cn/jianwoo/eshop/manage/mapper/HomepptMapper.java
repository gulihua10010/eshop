package cn.jianwoo.eshop.manage.mapper;

import cn.jianwoo.eshop.manage.entity.Homeppt;
import cn.jianwoo.eshop.manage.entity.Menu;

import java.util.List;

public interface HomepptMapper {

    int insert(Homeppt homeppt);
    int update(Homeppt homeppt);
    int delete(Homeppt homeppt);
    int deleteAll();
    List<Homeppt> getHomepptlist() ;
    List<Homeppt> getHomepptlistAndOn() ;
    Homeppt getHomepptById(Integer id) ;
}