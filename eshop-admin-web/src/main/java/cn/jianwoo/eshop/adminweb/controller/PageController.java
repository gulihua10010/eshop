package cn.jianwoo.eshop.adminweb.controller;

import cn.jianwoo.eshop.api.AddressService;
import cn.jianwoo.eshop.api.OrderService;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.item.api.ItemProvService;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.manage.entity.*;
import cn.jianwoo.eshop.user.api.UserService;
import cn.jianwoo.eshop.webconfig.api.HomepptService;
import cn.jianwoo.eshop.webconfig.api.MenuService;
import cn.jianwoo.eshop.webconfig.api.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {
@Autowired
    AddressService addressService;
    @Autowired
    ItemService itemService;
    @Autowired
    ItemCatService itemCatService;
    @Autowired
    MenuService menuService;
    @Autowired
    ItemProvService itemProvService;
    @Autowired
    HomepptService homepptService;
    @Autowired
    WebConfigService webConfigService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/catlist")
    public String catlist(Model model){
        List<ItemCat> itemCats=itemCatService.getItemCatByParentId(0L);
        System.out.println(itemCats);
        model.addAttribute("cats",itemCats);
        return "pages/itemcat";
    }
    @RequestMapping("/demo")
    public String demo(){
        return "pages/demo";
    }
    @RequestMapping("/item")
    public String item(){
        return "pages/item";
    }

    @RequestMapping("/selectitemcat")
    public String selectitemcat(){
        return "pages/selectitemcat";
    }
    @RequestMapping("/Item/editItem")
    public String editItem(Long id,Model model){
        System.out.println(id);
        Item item=itemService.getById(id);
        List<ItemProv> itemProvs=itemProvService.getItemProvByIid(id);
        System.out.println(item);
        model.addAttribute("item",item);
        model.addAttribute("itemProvs",itemProvs);
        return "pages/item-edit";
    }
    @RequestMapping("/Item/addItem")
    public String addtItem(){
        return "pages/item-add";
    }
    @RequestMapping("/webconfig")
    public String webconfig(Model model){
        List<WebConfig > webConfigs=webConfigService.geWebConfiglist();
        Map<String,String> web=new HashMap<>();

        for (WebConfig w:webConfigs   ) {
            web.put(w.getKey(),w.getValue());
        }
        System.out.println(web);
        model.addAttribute("webconfig",web);
        return "pages/webconfig";
    }
    @RequestMapping("/menuset")
    public String menuset(Model model){
        model.addAttribute("menu",menuService.getMenulist());
        model.addAttribute("count",menuService.count());

        return "pages/menuset";
    }
    @RequestMapping("/addmenu")
    public String addmenu(){
        return "pages/addmenu";
    }
    @RequestMapping("/homeppt")
    public String homeppt(Model model){
        List<Homeppt> homeppts=homepptService.getHomepptlist();

        model.addAttribute("ppt",homeppts);
        return "pages/homeppt";
    }
    @RequestMapping("/editmenu")
    public String editmenu(Model model,Integer id){
        model.addAttribute("menu",menuService.getMenuById(id));
        return "pages/editmenu";
    }
    @RequestMapping("/userlist")
    public String userlist( ){
        return "pages/userlist";
    }

    @RequestMapping("/user/detail")
    public String getuser(Model model, Long id){
        User user=userService.getById(id );
        //mingt   3zhan
        model.addAttribute("user",user);
        return "pages/getuser";
    }
    @RequestMapping("/console")
    public String console(Model model, Long id){
        Integer orders=orderService.getcount();
        Integer ordersfa=orderService.getcountbyfa();
        Integer items=itemService.getcount();
        Integer users=userService.getcount();
        System.out.println(ordersfa);
        model.addAttribute("orders",orders);
        model.addAttribute("ordersfa",ordersfa);
        model.addAttribute("items",items);
        model.addAttribute("users",users);
        return "console";
    }
    @RequestMapping("/adduserform")
    public String adduserform( ){
        return "pages/adduserform";
    }


    @RequestMapping("/orderlist")
    public String orderlist( ){
        return "pages/orderlist";
    }

    @RequestMapping("/uporder")
    public String uporder( ){
        return "pages/uporder";
    }

    @RequestMapping("/order/detail")
    public String orderdetail(Model model, String oid,  HttpServletRequest request){
        HttpSession session=request.getSession();
        String kw=request.getParameter("oid");

         Order   order=orderService.getOrderbyoId(oid);

        Address address=addressService.getaddressbyid(order.getAddressId());
        model.addAttribute("order",order);

        model.addAttribute("address",address);

        return  "pages/orderdetail";

    }
    @RequestMapping("/login")
    public String login( ){
        return "login";
    }
}
