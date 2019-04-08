package cn.jianwooeshop.order.service.impl;

import cn.jianwoo.eshop.api.OrderService;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.common.utils.IDUtils;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.entity.Order;
import cn.jianwoo.eshop.manage.entity.OrderItem;
import cn.jianwoo.eshop.manage.entity.User;
import cn.jianwoo.eshop.manage.mapper.ItemMapper;
import cn.jianwoo.eshop.manage.mapper.OrderItemMapper;
import cn.jianwoo.eshop.manage.mapper.OrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.deploy.util.OrderedHashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Value("${ORDER_ITEM}")
    private  String  ORDER_ITEM;
    @Transactional
    @Override
    public EShopResult addOrder(Order record) {
        System.out.println(record);
        if (record!=null){
            List<Order> orders=orderMapper.getOrderByToken(record.getToken());
            System.out.println(orders);
            if (orders.size()>0){
               return EShopResult.error("订单重复提交");
            }
            record.setOrderId(IDUtils.genOrderId());
            record.setCreateTime(new Timestamp(new Date().getTime()));
            record.setConsignTime(new Timestamp(new Date().getTime()));
            record.setCloseTime(new Timestamp(new Date().getTime()));
            record.setEndTime(new Timestamp(new Date().getTime()));
            record.setUpdateTime(new Timestamp(new Date().getTime()));
            record.setPaymentTime(new Timestamp(new Date().getTime()));
            record.setStatus(3);
            orderMapper.insert(record);
            return  EShopResult.ok(record);

        }
        return EShopResult.error("订单不能为空");
    }
    @Transactional
    @Override
    public EShopResult updateOrder(Order record) {
        if (record!=null&&record.getId()!=null){
            record.setUpdateTime(new Timestamp(new Date().getTime()));
            orderMapper.update(record);
            return  EShopResult.ok();

        }
        return EShopResult.error("订单不能为空");
    }

    @Transactional
    @Override
    public EShopResult deleteOrder(Order record) {
        if (record!=null&&record.getId()!=null){
            orderMapper.delete(record);
            OrderItem orderItem=new OrderItem();
            orderItem.setOrderId(record.getOrderId());
            orderItemMapper.deletebyoid(orderItem);
            return  EShopResult.ok();

        }
        return EShopResult.error("订单不能为空");
    }

    @Transactional
    @Override
    public EShopResult createOrder(Order record, List<Item> items) {
        System.out.println("daaaaa");
        System.out.println(record);
        System.out.println(items);
        EShopResult result=addOrder(record);
        System.out.println(result);
        if (result.getStatus()==200){
            Order order= (Order) result.getData();
            if (order!=null){
                System.out.println("dddddddddddddd");
                System.out.println(items);
                for (Item i:items){
                    OrderItem orderItem=new OrderItem();
                    System.out.println(i.getSelnum());
                    orderItem.setItemId(i.getId());
                    orderItem.setNum(i.getSelnum());
                    orderItem.setTitle(i.getTitle());
                    orderItem.setOrderId(order.getOrderId());
                    orderItem.setPrice(i.getPrice());
                    orderItem.setPicPath(i.getImage());
                    System.out.println("=${item.picPath}");
                    System.out.println(orderItem);
                    orderItemMapper.insert(orderItem);
                }
        return  EShopResult.ok("订单提交成功",order.getOrderId());
            }


        }
        return result;
    }

    @Override
    public EShopResult addOrderItemRedis(Long itemId, Integer num,Long uid) {
        List<Item > items = (List<Item>) redisTemplate.opsForValue().get(ORDER_ITEM+":"+uid);
//        if (item!=null){
//
//            return  EShopResult.error("你有订单未提交，请先提交");
//        }
        Item item=new Item();
        item=itemMapper.getById(itemId);
        if (item==null||item.getStatus()!=1){
            return  EShopResult.error("商品不存在或者已经下架了");
        }
        item.setSelnum(num);
       items=new ArrayList<>();
        items.add(item);
        System.out.println(items);
        redisTemplate.opsForValue().set(ORDER_ITEM+":"+uid,items);
        return EShopResult.ok();
    }

    @Override
    public Order getOrderbyoId(String oid) {
        return orderMapper.getOrderbyoId(oid);
    }

    @Override
    public Order getOrderbyId(Long id) {
        return orderMapper.getOrderbyId(id);
    }

    @Override
    public List<Order> getOrderListbyMap(Long uid,Integer status,String oid) {
        Map<String,Object> map=new HashMap<>();
        map.put("uid",uid);
        map.put("oid",oid);
        map.put("status",status);
        System.out.println(oid);
        return orderMapper.getOrderListbyMap(map);
    }

    @Override
    public Integer getcount() {
        return orderMapper.getcount();
    }

    @Override
    public Integer getcountbyfa() {
        return orderMapper.getcountbyfa();
    }

    @Override
    public List<Order> getOrderWithUser() {
        return orderMapper.getOrderWithUser();
    }

    @Override
    public LayuiResult getOrderWithUserWithPage(int page, int limit, String oid) {
        PageHelper.startPage(page,limit);
        List<Order> orders=null;
        if (oid==null||oid==""){

            orders=orderMapper.getOrderWithUser();
        }else {
            orders=orderMapper.getOrderWithUseWithoid(oid);
        }
        PageInfo<Order> info=new PageInfo<>(orders);
        System.out.println(info);
        return LayuiResult.ok(info.getTotal(),orders);
    }

    @Override
    public LayuiResult getOrderWithUserByStatus(int page, int limit,  Integer status) {
        PageHelper.startPage(page,limit);
        List<Order> orders=null;
        orders=orderMapper.getOrderWithUserByStatus(status);

        PageInfo<Order> info=new PageInfo<>(orders);
        return LayuiResult.ok(info.getTotal(),orders);
    }

    @Override
    public List<Order> getOrderByuid(Long uid) {
        return orderMapper.getOrderByuid(uid);
    }

    @Override
    public List<Order> getOrderByuidandstatus(Long uid,Integer status){
        Map<String,Object> map=new HashMap<>();
        map.put("uid",uid);
        map.put("status",status);
        return orderMapper.getOrderByuidandstatus(map);
    }
}
