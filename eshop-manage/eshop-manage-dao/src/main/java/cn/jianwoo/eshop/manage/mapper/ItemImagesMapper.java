package cn.jianwoo.eshop.manage.mapper;

import cn.jianwoo.eshop.manage.entity.ItemImages;
import cn.jianwoo.eshop.manage.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface ItemImagesMapper {

    int insert(ItemImages itemImages);
    int update(ItemImages itemImages);
    int delete(ItemImages itemImages);
    int deleteByIid(Long iid);
    List<ItemImages> getOrderItemListByMap(Map<String, Object> params) ;
    ItemImages getItemImagesById(Long id) ;
    List<ItemImages> getItemImagesByIid(Long Iid) ;
}