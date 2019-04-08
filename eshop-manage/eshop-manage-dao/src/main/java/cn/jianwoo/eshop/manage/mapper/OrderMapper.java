package cn.jianwoo.eshop.manage.mapper;


import cn.jianwoo.eshop.manage.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

    int insert(Order record);
    int update(Order record);
    int delete(Order record);
    Order getOrderbyoId(String oid);
    Order getOrderbyId(Long id);
   List<Order> getOrderListbyMap(Map<String, Object> param);
   List<Order> getOrderByToken(String token);
   List<Order> getOrderByuid(Long uid);
   List<Order> getOrderWithUser();
   List<Order> getOrderWithUseWithoid(String oid);
   List<Order> getOrderWithUserByStatus(Integer status);
   List<Order> getOrderByuidandstatus(Map<String, Object> param);
    Integer getcount();
    Integer getcountbyfa();
}