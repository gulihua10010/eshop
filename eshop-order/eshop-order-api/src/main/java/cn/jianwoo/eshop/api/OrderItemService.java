package cn.jianwoo.eshop.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderItemService {
    EShopResult insert(OrderItem record);
    EShopResult update(OrderItem record);
    EShopResult delete(OrderItem record);
    EShopResult deletebyoid(OrderItem record);
    List<OrderItem> getOrderItemListByMap(Map<String, Object> params) ;
    OrderItem getOrderItem(Map<String, Object> params) ;
    OrderItem getOrderItemById(Long id) ;
    List<OrderItem> getOrderItemByoId(String orderId) ;
    List<OrderItem> getOrderItemList() ;
}
