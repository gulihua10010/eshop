package cn.jianwoo.eshop.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    EShopResult addOrder(Order record);
    EShopResult updateOrder(Order record);
    EShopResult deleteOrder(Order record);
    EShopResult createOrder(Order record, List<Item > items);
    EShopResult addOrderItemRedis(Long itemId,Integer num,Long uid) ;
    Order getOrderbyoId(String oid);
    Order getOrderbyId(Long id);
    List<Order> getOrderListbyMap(Long uid,Integer status,String oid);
    Integer getcount();
    Integer getcountbyfa();
    List<Order> getOrderWithUser();
    LayuiResult getOrderWithUserWithPage(int page, int limit, String oid);
    LayuiResult getOrderWithUserByStatus(int page, int limit, Integer status);
    List<Order> getOrderByuid(Long uid);
    List<Order> getOrderByuidandstatus(Long uid,Integer status);
}
