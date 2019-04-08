package cn.jianwoo.eshop.adminweb.controller;

import cn.jianwoo.eshop.api.OrderService;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.manage.entity.Order;
import cn.jianwoo.eshop.manage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @RequestMapping("/getorderlist")
    public LayuiResult getOrderList(Integer page, Integer limit,String kw){

        return  orderService.getOrderWithUserWithPage(page,limit,kw);


    }
    @RequestMapping("/getorderlistwiths")
    public LayuiResult getorderlistwiths(Integer page, Integer limit ){

        return orderService.getOrderWithUserByStatus(page,limit,3);


    }

    @RequestMapping("/updatestatus")
    public EShopResult updatestatus(HttpServletRequest request, Long id, Integer status){
        System.out.println(status);
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
}
