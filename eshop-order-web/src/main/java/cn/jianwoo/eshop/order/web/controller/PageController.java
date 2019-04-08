package cn.jianwoo.eshop.order.web.controller;

import cn.jianwoo.eshop.api.AddressService;
import cn.jianwoo.eshop.api.OrderService;
import cn.jianwoo.eshop.cart.api.CartService;
import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.manage.entity.*;
import cn.jianwoo.eshop.webconfig.api.MenuService;
import cn.jianwoo.eshop.webconfig.api.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
    @Autowired
    CartService cartService;
    @Autowired
    ItemCatService itemCatService;
    @Autowired
    MenuService menuServicel;
    @Value("${ORDER_URL}")
    private  String ORDER_URL;
    @Value("${SSO_URL}")
    private  String SSO_URL;
    @Value("${ITEM_URL}")
    private  String ITEM_URL;
    @Value("${HOME_URL}")
    private  String HOME_URL;
    @Value("${USER_URL}")
    private  String USER_URL;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    WebConfigService webConfigService;
    @Autowired
    AddressService addressService;
@Autowired
    OrderService orderService;
    private  boolean isLogin=false;

    @RequestMapping("/order/settlement")
    public String settlement(Model model, String token, Integer type , HttpServletRequest request){
        HttpSession session=request.getSession();

        //top
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig");
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
//        ItemCat itemCat1=null;
//        ItemCat itemCat2=null;
//        ItemCat itemCat3=null;
//        System.out.println(item);
//        itemCat1=itemCatService.getItemCatNameById(item.getCid());
//        System.out.println(itemCat1);
//        itemCat1.setItemCats(itemCatService.getItemCatByParentId(itemCat1.getParentId()));
//        System.out.println(itemCat1.getParentId());
//        if (itemCat1!=null&&itemCat1.getParentId()!=0){
//            itemCat2=itemCatService.getItemCatNameById(itemCat1.getParentId());
//            itemCat2.setItemCats(itemCatService.getItemCatByParentId(itemCat2.getParentId()));
//
//        }
//        if (itemCat2!=null&&itemCat2.getParentId()!=0){
//            itemCat3=itemCatService.getItemCatNameById(itemCat2.getParentId());
//            itemCat3.setItemCats(itemCatService.getItemCatByParentId(itemCat3.getParentId()));
//
//        }
        List<ItemCat> itemCats=itemCatService.getItemCatByParentId(0L);
        System.out.println(itemCats);
        List<ItemCatLayuiTree> itemCatstree=itemCatService.getItemCatListTree();
        model.addAttribute("itemCats",itemCats);
//        model.addAttribute("cat1",itemCat1);
//        model.addAttribute("cat2",itemCat2);
//        model.addAttribute("cat3",itemCat3);
        model.addAttribute("menu",menuServicel.getMenulistAndOn());
        model.addAttribute("itemCatstree",itemCatstree);
        session.setAttribute("webconfig",web);
        model.addAttribute("webconfig",web);
        model.addAttribute("isLogin",isLogin);
        model.addAttribute("itemurl",ITEM_URL);
        model.addAttribute("ssourl",SSO_URL);
        model.addAttribute("homeurl",HOME_URL);
        model.addAttribute("orderurl",ORDER_URL);
        model.addAttribute("userurl",USER_URL);





//验证登录
        String usertoken= (String) session.getAttribute("LOGIN_TOKEN_ON");
        System.out.println(isLogin);
        System.out.println("usertoken "+usertoken);
        System.out.println(session.getId() );
        User user=null;
        if (usertoken!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + usertoken);
        }
        System.out.println(user!=null);

        if (user!=null){
            isLogin=true;
        }else {
            isLogin=false;
        }
        if (isLogin==false){

            return  "redirect:"+SSO_URL+"/login";
        }
        System.out.println(user!=null);
        System.out.println(user);
        System.out.println(isLogin);
        List<Address> addresses=null;
        Address address=null;
        List<Item> items=null;
        Double totalprice=0.0;
        boolean isdefault=false;
        if (user!=null){
            System.out.println("ORDER_ITEM"+":"+user.getId());
                items= (List<Item>) redisTemplate.opsForValue().get("ORDER_ITEM"+":"+user.getId());
            System.out.println(items);
        if (items!=null){
            for (Item item:items){
                totalprice+=item.getPrice()/100.00*item.getSelnum();
            }
        }


            //address
            addresses=addressService.getAddresslistByUidDefault(user.getId());
            isdefault=true;
            if (addresses==null||addresses.size()==0){
                isdefault=false;
                addresses=addressService.getAddresslistByUid(user.getId());
            }
            System.out.println(addresses);

        }

        model.addAttribute("user",user);
        model.addAttribute("items",items);
        boolean ishasAddress=false;
        if (addresses!=null&&addresses.size()>0){
            address=addresses.get(0);
            ishasAddress=true;
        }
        System.out.println(ishasAddress);
        DecimalFormat    df   = new DecimalFormat("######0.00");
        System.out.println(df.format(totalprice));

        model.addAttribute("ishasAddress",ishasAddress);
        model.addAttribute("address",address);
        model.addAttribute("isdefault",isdefault);
        model.addAttribute("token",token);
        model.addAttribute("totalprice",df.format(totalprice));

        return  "index";

    }
    @RequestMapping("/orderfinish")
    public  String  orderFinish(String orderid, HttpServletRequest request,Model model){

        HttpSession session=request.getSession();

        //top
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig");
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
        List<ItemCat> itemCats=itemCatService.getItemCatByParentId(0L);
        System.out.println(itemCats);
        List<ItemCatLayuiTree> itemCatstree=itemCatService.getItemCatListTree();
        model.addAttribute("itemCats",itemCats);
//        model.addAttribute("cat1",itemCat1);
//        model.addAttribute("cat2",itemCat2);
//        model.addAttribute("cat3",itemCat3);
        model.addAttribute("menu",menuServicel.getMenulistAndOn());
        model.addAttribute("itemCatstree",itemCatstree);
        session.setAttribute("webconfig",web);
        model.addAttribute("webconfig",web);
        model.addAttribute("isLogin",isLogin);
        model.addAttribute("itemurl",ITEM_URL);
        model.addAttribute("ssourl",SSO_URL);
        model.addAttribute("homeurl",HOME_URL);
        model.addAttribute("userurl",USER_URL);

        model.addAttribute("orderurl",ORDER_URL);




//验证登录
        String usertoken= (String) session.getAttribute("LOGIN_TOKEN_ON");
        System.out.println(isLogin);
        System.out.println("usertoken "+usertoken);
        System.out.println(session.getId() );
        User user=null;
        if (usertoken!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + usertoken);
        }

        if (user!=null){
            isLogin=true;
        }else {
            isLogin=false;
        }
        System.out.println(user);
        System.out.println(isLogin);
       Order order=orderService.getOrderbyoId(orderid);
        model.addAttribute("user",user);
        model.addAttribute("orderid",orderid);
        model.addAttribute("price",order.getPayment());



        return  "orderfinish";


    }
    @RequestMapping("/forpayment")
    public  String  forpayment(String orderid, HttpServletRequest request,Model model){

        HttpSession session=request.getSession();

        //top
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig");
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
        List<ItemCat> itemCats=itemCatService.getItemCatByParentId(0L);
        System.out.println(itemCats);
        List<ItemCatLayuiTree> itemCatstree=itemCatService.getItemCatListTree();
        model.addAttribute("itemCats",itemCats);
//        model.addAttribute("cat1",itemCat1);
//        model.addAttribute("cat2",itemCat2);
//        model.addAttribute("cat3",itemCat3);
        model.addAttribute("menu",menuServicel.getMenulistAndOn());
        model.addAttribute("itemCatstree",itemCatstree);
        session.setAttribute("webconfig",web);
        model.addAttribute("webconfig",web);
        model.addAttribute("isLogin",isLogin);
        model.addAttribute("itemurl",ITEM_URL);
        model.addAttribute("itemurl",ITEM_URL);
        model.addAttribute("ssourl",SSO_URL);
        model.addAttribute("homeurl",HOME_URL);
        model.addAttribute("orderurl",ORDER_URL);
        model.addAttribute("userurl",USER_URL);




//验证登录
        String usertoken= (String) session.getAttribute("LOGIN_TOKEN_ON");
        System.out.println(isLogin);
        System.out.println("usertoken "+usertoken);
        System.out.println(session.getId() );
        User user=null;
        if (usertoken!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + usertoken);
        }

        if (user!=null){
            isLogin=true;
        }else {
            isLogin=false;
        }
        System.out.println(user);
        System.out.println(isLogin);
        Order order=orderService.getOrderbyoId(orderid);
        model.addAttribute("user",user);
        model.addAttribute("orderid",orderid);
        model.addAttribute("price",order.getPayment());



        return  "payment";


    }

    @RequestMapping("/carts/mycatlist")
    public String mycatlist(Model model ,   HttpServletRequest request){
        HttpSession session=request.getSession();

        //top
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig");
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
        List<ItemCat> itemCats=itemCatService.getItemCatByParentId(0L);
        System.out.println(itemCats);
        List<ItemCatLayuiTree> itemCatstree=itemCatService.getItemCatListTree();
        model.addAttribute("itemCats",itemCats);
        model.addAttribute("menu",menuServicel.getMenulistAndOn());
        model.addAttribute("itemCatstree",itemCatstree);
        session.setAttribute("webconfig",web);
        model.addAttribute("webconfig",web);
        model.addAttribute("isLogin",isLogin);
        model.addAttribute("itemurl",ITEM_URL);
        model.addAttribute("ssourl",SSO_URL);
        model.addAttribute("homeurl",HOME_URL);
        model.addAttribute("orderurl",ORDER_URL);
        model.addAttribute("userurl",USER_URL);





//验证登录
        String usertoken= (String) session.getAttribute("LOGIN_TOKEN_ON");
        System.out.println(isLogin);
        System.out.println("usertoken "+usertoken);
        System.out.println(session.getId() );
        User user=null;
        if (usertoken!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + usertoken);
        }
        System.out.println(user!=null);

        if (user!=null){
            isLogin=true;
        }else {
            isLogin=false;
        }
        if (isLogin==false){

            return  "redirect:"+SSO_URL+"/login";
        }
        System.out.println(user!=null);
        System.out.println(user);
        System.out.println(isLogin);
        List<Item> itemList=null;
        if (user!=null){
         itemList  =cartService.getCartList(user.getId());
        }
        boolean isempty=true;
        if (itemList.size()>0){
            isempty=false;
        }
        System.out.println(isempty);
        model.addAttribute("user",user);
        model.addAttribute("items",itemList);
        model.addAttribute("isempty",isempty);



        return  "catlist";

    }
}
