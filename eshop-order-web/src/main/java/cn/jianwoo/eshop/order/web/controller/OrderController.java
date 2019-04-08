package cn.jianwoo.eshop.order.web.controller;

import cn.jianwoo.eshop.api.OrderItemService;
import cn.jianwoo.eshop.api.OrderService;
import cn.jianwoo.eshop.cart.api.CartService;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.utils.IDUtils;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.entity.Order;
import cn.jianwoo.eshop.manage.entity.OrderItem;
import cn.jianwoo.eshop.manage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class OrderController {
@Autowired
    RedisTemplate redisTemplate;
@Autowired
    ItemService itemService;
@Autowired
    OrderItemService orderItemService;
@Autowired
    OrderService orderService;
@Autowired
    CartService cartService;
    @RequestMapping("/orders/submit")
    public EShopResult ordersumit(@RequestBody Order order, HttpServletRequest request){

        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
        System.out.println(token);
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            if (user == null) {
                return EShopResult.error("请先登录");
            }
            Long uid = user.getId();
            order.setUid(uid);
            order.setPostFee(0.0);
            order.setBuyerNick(user.getNickName());
            List<Item> itemList=new ArrayList<>();
           itemList= (List<Item>) redisTemplate.opsForValue().get("ORDER_ITEM"+":"+user.getId());

            System.out.println(itemList);
            System.out.println(order);
            System.out.println("create");
            EShopResult result=  orderService.createOrder(order,itemList);
            if (result.getStatus()==200){
                redisTemplate.opsForValue().set("ORDER_ITEM"+":"+user.getId(),null);
                 String orderid= (String) result.getData();
                 List<OrderItem> items=orderItemService.getOrderItemByoId(orderid);
                 for (OrderItem orderItem:items){
                     cartService.deleteCartItem(uid,orderItem.getItemId());
                 }


            }
            return  result;

        }
        System.out.println(order);
        return  EShopResult.error("错误");


    }

    @RequestMapping("/delcats")
    public EShopResult delcats(Long id, HttpServletRequest request){
        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
        System.out.println(token);
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            if (user == null) {
                return EShopResult.error("请先登录");
            }
            Long uid = user.getId();
         return    cartService.deleteCartItem(uid,id);

        }
        return  EShopResult.error("失败");
    }

    @RequestMapping("/setitemorder")
    public EShopResult setitemorder(@RequestBody List<Item> items, HttpServletRequest request){
        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
        System.out.println(token);
        System.out.println(items);
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            if (user == null) {
                return EShopResult.error("请先登录");
            }
            Long uid = user.getId();
            List<Item> itemList=new ArrayList<>();
            for (Item item:items){
                Integer num=item.getSelnum();
                item=itemService.getById(item.getId());
                System.out.println(item);
                item.setSelnum(num);
                itemList.add(item);
            }
            System.out.println(itemList);
            redisTemplate.opsForValue().set("ORDER_ITEM:"+uid,itemList);
            String token1=IDUtils.randomChar(15);
            return    EShopResult.ok("订单提交成功，正在结算",token1);

        }
        return  EShopResult.error("失败");
    }

    @RequestMapping("/updatestatus")
    public EShopResult updatestatus(  HttpServletRequest request,Long id,Integer status){
        System.out.println(status);
        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
        System.out.println(token);
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            if (user == null) {
                return EShopResult.error("请先登录");
            }
            Long uid = user.getId();
            Order order=new Order();
            order.setId(id);
            order.setStatus(status);
            EShopResult result=orderService.updateOrder(order);
            String msg="ok";
            switch (status){

                case 2 :msg=""; break;
                case 4 :msg="发货成功"; break;
                case 5 :msg="收货成功"; break;
                case 0 :msg="订单取消成功"; break;
                case 6 :msg="评价成功"; break;
            }
            if (result.getStatus()==200){
                return    EShopResult.ok(msg,null);

            }else {
                return  result;
            }

        }
        return  EShopResult.error("失败");
    }
}
