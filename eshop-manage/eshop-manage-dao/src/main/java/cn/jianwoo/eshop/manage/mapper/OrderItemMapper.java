package cn.jianwoo.eshop.manage.mapper;

import cn.jianwoo.eshop.manage.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderItemMapper {

    int insert(OrderItem record);
    int update(OrderItem record);
    int delete(OrderItem record);
    int deletebyoid(OrderItem record);
    List<OrderItem> getOrderItemListByMap(Map<String, Object> params) ;
     OrderItem getOrderItem(Map<String, Object> params) ;
    OrderItem getOrderItemById(Long id) ;
    List<OrderItem> getOrderItemByoId(String orderId) ;
    List<OrderItem> getOrderItemList() ;
}