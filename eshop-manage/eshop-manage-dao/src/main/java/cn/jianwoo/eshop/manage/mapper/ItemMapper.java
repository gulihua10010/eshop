package cn.jianwoo.eshop.manage.mapper;


import cn.jianwoo.eshop.manage.entity.Item;
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Map;

public interface ItemMapper {

    Item getById(Long id);

    List<Item> getItemList();
    List<Item> getItemListByRamdom();
    List<Item> getItemListByRecommended();
    List<Item> getItemListByHot();
    List<Item> getItemListByNews();
    List<Item> getItemListByNewsByn(Integer n);
    List<Item> search(String kw);
    List<Item> getItemListByCatLimitByn(Map<String,Object> params);
    List<Item> getItemListByMaps(Map<String,Object> maps);
    List<Item> getItemListByMap(Map<String, Object> params);
    List<Item> getItemListByRecommendedWithMap(Map<String, Object> params);
    int insert(Item item);
    Integer countRecommended();
    Integer getcount();
    int update(Item item);
    int delete(Item item);
}