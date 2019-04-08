package cn.jianwoo.eshop.item.web.controller;

import cn.jianwoo.eshop.api.OrderService;
import cn.jianwoo.eshop.cart.api.CartService;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.utils.IDUtils;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.entity.User;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ItemController {
@Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/addcart")
    public EShopResult addcart(HttpServletRequest request){
        String itemid=request.getParameter("goodsId");
        String goodsSpecId=request.getParameter("goodsSpecId");
        String buyNum=request.getParameter("buyNum");
        String type=request.getParameter("type");
        String rnd=request.getParameter("rnd");
        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
        System.out.println(token);
        System.out.println(buyNum);
        System.out.println(buyNum);
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            if (user==null){
                return  EShopResult.error("请先登录");
            }
               Long uid=user.getId();
            cartService.addCart(uid,Long.valueOf(itemid),Integer.valueOf(buyNum));
            return  EShopResult.ok("添加成功",null);
        }else{

            return  EShopResult.error("请先登录");
        }
    }
    @RequestMapping("/getcatlist")
    public  EShopResult getcatlist(HttpServletRequest request){
        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
        System.out.println(token);
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            Long uid=user.getId();
          List<Item> itemList  =cartService.getCartList(uid);
            List<Item> itemList1=new ArrayList<>();
            Double totalprice=0.0;
            for (Item i: itemList ) {
                totalprice=totalprice+i.getSelnum()*(i.getPrice()/100.00);
                i.setPrice1(i.getPrice()/100.00);
                i.setTitle(i.getTitle().substring(0,26)+"...");
                itemList1.add(i);
            }
            Map<String ,Object>  res=new HashMap<>();
            res.put("list",itemList1);
            res.put("count",itemList.size());
            res.put("totalprice",totalprice);
            return  EShopResult.ok(res);
        }

        return  EShopResult.error();
    }
    @RequestMapping("/delcart")
    public  EShopResult delcart(HttpServletRequest request){
        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
                String id=request.getParameter("id");
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            if (user!=null){
                Long uid=user.getId();
                Long itemid=Long.valueOf(id);
            return     cartService.deleteCartItem(uid,itemid);

            }
            return  EShopResult.error("错误1");
        }

        return  EShopResult.error("错误2");
    }
    @RequestMapping("/saveorderitem")
    public  EShopResult saveorderitem(HttpServletRequest request){
        String itemid=request.getParameter("goodsId");
        String goodsSpecId=request.getParameter("goodsSpecId");
        String buyNum=request.getParameter("buyNum");
        String type=request.getParameter("type");
        String rnd=request.getParameter("rnd");
        String token= (String) request.getSession().getAttribute("LOGIN_TOKEN_ON");
        System.out.println(token);
        System.out.println(buyNum);
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
            System.out.println(user);
            if (user==null){
                return  EShopResult.error("请先登录");
            }
            Long uid=user.getId();
//         redisTemplate.opsForValue().set("ORDER_ITEM"+":"+uid,);
              orderService.addOrderItemRedis(Long.valueOf(itemid),Integer.valueOf(buyNum),uid);
              String orderTokens=IDUtils.randomChar(15);
Map<String,String> res=new HashMap<>();
res.put("token",orderTokens);
res.put("uid",uid.toString());
            return  EShopResult.ok(res);
        }else{

            return  EShopResult.error("请先登录");
        }
    }
}
