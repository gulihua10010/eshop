package cn.jianwoo.eshop.usercenter.web.controller;

import cn.jianwoo.eshop.api.AddressService;
import cn.jianwoo.eshop.api.OrderService;
import cn.jianwoo.eshop.cart.api.CartService;
import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.manage.entity.*;
import cn.jianwoo.eshop.user.api.UserService;
import cn.jianwoo.eshop.webconfig.api.MenuService;
import cn.jianwoo.eshop.webconfig.api.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    UserService userService;
@Autowired
    OrderService orderService;
    private  boolean isLogin=false;


    @RequestMapping("/user/noshou")
    public String settlement(Model model,   HttpServletRequest request){
        HttpSession session=request.getSession();
        String kw=request.getParameter("oid");
        //top
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig") ;
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
//
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

//获取count




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
        List<Order> orders=null;

           int count1=0;
           int count2=0;
           int count3=0;
           int count4=0;
           int count5=0;

        if (user!=null){

            //获取count

                List<Order> orderc=orderService.getOrderByuid(user.getId());

            for (Order o:orderc) {
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }
            System.out.println(kw);
            System.out.println( );
            System.out.println(kw=="");
            if (kw==null||kw==""){
                orders=orderService.getOrderByuidandstatus(user.getId(),3);
                System.out.println(11111);
            }else {

                orders=orderService.getOrderListbyMap(user.getId(),3,kw);
                System.out.println(2222);

            }
            System.out.println(orders);
        }

        model.addAttribute("user",user);
        model.addAttribute("orders",orders);

        model.addAttribute("menuid",2);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);
        return  "notshou";

    }

    @RequestMapping("/user/nopjorder")
    public String nopjorder(Model model,   HttpServletRequest request){
        HttpSession session=request.getSession();
        String kw=request.getParameter("oid");
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
//
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
        List<Order> orders=null;

        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        int count5=0;

        if (user!=null){

            //获取count
            List<Order> orderc=orderService.getOrderByuid(user.getId());
            for (Order o:orderc) {
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }
            if (kw==null||kw==""){
                orders=orderService.getOrderByuidandstatus(user.getId(),5);


            }else {
                orders=orderService.getOrderListbyMap(user.getId(),5,kw);


            }
            System.out.println(orders);
        }

        model.addAttribute("user",user);
        model.addAttribute("orders",orders);
 ;
        model.addAttribute("menuid",3);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);
        return  "pjorder";

    }

    @RequestMapping("/user/nopayorder")
    public String   nopayorder (Model model,   HttpServletRequest request){
        HttpSession session=request.getSession();
        String kw=request.getParameter("oid");
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
//
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
        List<Order> orders=null;
        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        int count5=0;

        if (user!=null){

            //获取count
            List<Order> orderc=orderService.getOrderByuid(user.getId());
            for (Order o:orderc) {
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }
            if (kw==null||kw==""){
                orders=orderService.getOrderByuidandstatus(user.getId(),1);


            }else {
                orders=orderService.getOrderListbyMap(user.getId(),1,kw);


            }
            System.out.println(orders);
        }

        model.addAttribute("user",user);
        model.addAttribute("orders",orders);

        model.addAttribute("menuid",1);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);
        return  "nopay";

    }


    @RequestMapping("/user/cancelorder")
    public String cancelorder(Model model,   HttpServletRequest request){
        HttpSession session=request.getSession();
        String kw=request.getParameter("oid");
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
//
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
        List<Order> orders=null;
        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        int count5=0;

        if (user!=null){

            //获取count
            List<Order> orderc=orderService.getOrderByuid(user.getId());
            for (Order o:orderc) {
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }
            if (kw==null||kw==""){
                orders=orderService.getOrderByuidandstatus(user.getId(),0);


            }else {
                orders=orderService.getOrderListbyMap(user.getId(),0,kw);


            }
            System.out.println(orders);
        }

        model.addAttribute("user",user);
        model.addAttribute("orders",orders);

        model.addAttribute("menuid",5);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);
        return  "cancel";

    }

    @RequestMapping("/user/complateorder")
    public String complateorder(Model model,   HttpServletRequest request){
        HttpSession session=request.getSession();
        String kw=request.getParameter("oid");
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
//
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
        List<Order> orders=null;

        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        int count5=0;

        if (user!=null){

            //获取count
            List<Order> orderc=orderService.getOrderByuid(user.getId());
            for (Order o:orderc) {
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }

            if (kw==null||kw==""){
                orders=orderService.getOrderByuidandstatus(user.getId(),5);
                List<Order> orders1=orderService.getOrderByuidandstatus(user.getId(),6);
                orders.addAll(orders1);

            }else {
                orders=orderService.getOrderListbyMap(user.getId(),5,kw);
                List<Order> orders1=orderService.getOrderListbyMap(user.getId(),6,kw);
                orders.addAll(orders1);

            }
            System.out.println(orders);
        }

        model.addAttribute("user",user);
        model.addAttribute("orders",orders);

        model.addAttribute("menuid",4);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);
        return  "complate";

    }

    @RequestMapping("/order/detail")
    public String orderdetail(Model model, String oid,  HttpServletRequest request){
        HttpSession session=request.getSession();
        String kw=request.getParameter("oid");
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
//
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
     Order order=null;
        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        int count5=0;

        if (user!=null){

            //获取count
            List<Order> orderc=orderService.getOrderByuid(user.getId());
            for (Order o:orderc) {
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }

     order=orderService.getOrderbyoId(oid);
        }
Address address=addressService.getaddressbyid(order.getAddressId());
        model.addAttribute("user",user);
        model.addAttribute("order",order);
        model.addAttribute("address",address);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);

        return  "detail";

    }

    @RequestMapping("/usercenter/index")
    public String usercenter(Model model,   HttpServletRequest request){
        HttpSession session=request.getSession();
        //top
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig") ;
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
//
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

//获取count




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
        List<Order> orders=new ArrayList<>();

        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        int count5=0;

        if (user!=null){

            //获取count

            List<Order> orderc=orderService.getOrderByuid(user.getId());
int t=0;
            for (Order o:orderc) {
                if (t<5){
                    orders.add(o);
                }
                t++;
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }

//            orders=orderService.getOrderByuid(user.getId());

            System.out.println(orders);
        }

        model.addAttribute("user",user);
        model.addAttribute("orders",orders);

        model.addAttribute("menuid",0);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);
        return  "usercenter";

    }

    @RequestMapping("/usercenter/my")
    public String my(Model model,   HttpServletRequest request){
        HttpSession session=request.getSession();
        //top
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig") ;
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
//
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

//获取count




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
        List<Order> orders=new ArrayList<>();

        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        int count5=0;

        if (user!=null){

            //获取count

            List<Order> orderc=orderService.getOrderByuid(user.getId());

            for (Order o:orderc) {
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }

//            orders=orderService.getOrderByuid(user.getId());

            System.out.println(orders);
        }

        model.addAttribute("user",userService.getById(user.getId()));

        model.addAttribute("menuid",7);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);
        return  "user";

    }

    @RequestMapping("/usercenter/addaddress")
    public String addaddress(Model model, Long id,  HttpServletRequest request){
        HttpSession session=request.getSession();
        //top
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig") ;
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
//
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

//获取count




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
        List<Order> orders=new ArrayList<>();
        Address  address=null;
        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        int count5=0;

        if (user!=null){

            //获取count

            List<Order> orderc=orderService.getOrderByuid(user.getId());

            for (Order o:orderc) {
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }

            if (id!=null&&id!=0L){
                address=addressService.getaddressbyid(id);

            }

//            orders=orderService.getOrderByuid(user.getId());
        }

        model.addAttribute("address", address);

        model.addAttribute("menuid",8);
        model.addAttribute("user",user);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);
        return  "addAddress";

    }



    @RequestMapping("/usercenter/address")
    public String address(Model model,   HttpServletRequest request){
        HttpSession session=request.getSession();
        //top
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig") ;
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
            web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }
//
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

//获取count




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
        List<Order> orders=new ArrayList<>();
        List<Address> addresses=new ArrayList<>();
        int count1=0;
        int count2=0;
        int count3=0;
        int count4=0;
        int count5=0;

        if (user!=null){

            //获取count

            List<Order> orderc=orderService.getOrderByuid(user.getId());

            for (Order o:orderc) {
                if (o.getStatus()==1) count1++;
                if (o.getStatus()==3) count2++;
                if (o.getStatus()==5) count3++;
                if (o.getStatus()==4||o.getStatus()==5) count4++;
                if (o.getStatus()==0) count5++;
            }

//            orders=orderService.getOrderByuid(user.getId());
            addresses=addressService.getAddresslistByUid(user.getId());
        }

        model.addAttribute("address", addresses);
        model.addAttribute("user",user);

        model.addAttribute("menuid",8);

        model.addAttribute("count1",count1);
        model.addAttribute("count2",count2);
        model.addAttribute("count3",count3);
        model.addAttribute("count4",count4);
        model.addAttribute("count5",count5);
        return  "address";

    }
}
