package cn.jianwoo.eshop.search.web.controller;

import cn.jianwoo.eshop.api.AddressService;
import cn.jianwoo.eshop.api.OrderService;
import cn.jianwoo.eshop.api.SearchService;
import cn.jianwoo.eshop.cart.api.CartService;
import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.item.api.ItemService;
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
import org.springframework.web.bind.annotation.RequestParam;

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
    ItemService itemService;
    @Autowired
    ItemCatService itemCatService;
    @Autowired
    MenuService menuServicel;
    @Value("${ORDER_URL}")
    private String ORDER_URL;
    @Value("${SSO_URL}")
    private String SSO_URL;
    @Value("${ITEM_URL}")
    private String ITEM_URL;
    @Value("${HOME_URL}")
    private String HOME_URL;
    @Value("${USER_URL}")
    private String USER_URL;
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
    @Autowired
    SearchService searchService;
    private boolean isLogin = false;


    @RequestMapping("/itemlist/search")
    public String settlement(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String kw = request.getParameter("oid");
        //top
        Map<String, String> web = null;
        web = (Map<String, String>) session.getAttribute("webconfig");
        if (web == null) {
            List<WebConfig> webConfigs = webConfigService.geWebConfiglistAndOn();
            web = new HashMap<>();
            for (WebConfig w : webConfigs) {
                web.put(w.getKey(), w.getValue());
            }
        }
//
        List<ItemCat> itemCats = itemCatService.getItemCatByParentId(0L);
        System.out.println(itemCats);
        List<ItemCatLayuiTree> itemCatstree = itemCatService.getItemCatListTree();
        model.addAttribute("itemCats", itemCats);
        model.addAttribute("menu", menuServicel.getMenulistAndOn());
        model.addAttribute("itemCatstree", itemCatstree);
        session.setAttribute("webconfig", web);
        model.addAttribute("webconfig", web);
        model.addAttribute("isLogin", isLogin);
        model.addAttribute("itemurl", ITEM_URL);
        model.addAttribute("ssourl", SSO_URL);
        model.addAttribute("homeurl", HOME_URL);
        model.addAttribute("orderurl", ORDER_URL);
        model.addAttribute("userurl", USER_URL);

//获取count


//验证登录
        String usertoken = (String) session.getAttribute("LOGIN_TOKEN_ON");
        System.out.println(isLogin);
        System.out.println("usertoken " + usertoken);
        System.out.println(session.getId());
        User user = null;
        if (usertoken != null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + usertoken);
        }
        System.out.println(user != null);

        if (user != null) {
            isLogin = true;
        } else {
            isLogin = false;
        }

        System.out.println(user != null);
        System.out.println(user);
        System.out.println(isLogin);
        List<Order> orders = null;
   ;


        model.addAttribute("like", itemService.getItemListByNewsByn(5));
        model.addAttribute("user", user);
        model.addAttribute("items",     searchService.search1(keyword,1,1));



        return "itemlist";

    }
}
