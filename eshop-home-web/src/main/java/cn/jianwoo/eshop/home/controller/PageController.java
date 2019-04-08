package cn.jianwoo.eshop.home.controller;

import cn.jianwoo.eshop.common.utils.CookieUtils;
import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.manage.entity.*;
import cn.jianwoo.eshop.webconfig.api.HomepptService;
import cn.jianwoo.eshop.webconfig.api.MenuService;
import cn.jianwoo.eshop.webconfig.api.WebConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//@CrossOrigin(origins = "http://localhost:8004",maxAge = 3600)
@Controller
public class PageController {
@Autowired
    RedisTemplate  redisTemplate;
    @Autowired
    ItemCatService itemCatService;
    @Autowired
    MenuService menuServicel;
    @Autowired
    HomepptService homepptService;
    @Autowired
    ItemService itemService;
    @Autowired
    WebConfigService webConfigService;
    @Value("${SSO_URL}")
    private  String SSO_URL;
    @Value("${ITEM_URL}")
    private  String ITEM_URL;
    @Value("${USER_URL}")
    private  String USER_URL;
    private  boolean isLogin=false;


    @Value("${HOME_URL}")
    private  String HOME_URL;
    @Value("${ORDER_URL}")
    private  String ORDER_URL;
    @RequestMapping(value = {"/index","/","index.html"})
    public String index(Model model, HttpServletRequest request, HttpServletResponse response){
        HttpSession session=request.getSession();
        long startTime=System.currentTimeMillis();   //获取开始时间
        List<ItemCat> itemCats=itemCatService.getItemCatByParentId(0L);
        System.out.println(itemCats);
        List<ItemCatLayuiTree> itemCatstree=itemCatService.getItemCatListTree();
        Map<String,String> web=null;
        web= (Map<String, String>) session.getAttribute("webconfig");
        if (web==null){
            List<WebConfig> webConfigs=webConfigService.geWebConfiglistAndOn();
           web=new HashMap<>();
            for (WebConfig w:webConfigs   ) {
                web.put(w.getKey(),w.getValue());
            }
        }

        model.addAttribute("webconfig",web);
        session.setAttribute("webconfig",web);

        long endTime=System.currentTimeMillis(); //获取结束时间
       String token= (String) session.getAttribute("LOGIN_TOKEN_ON");
        System.out.println("toekn "+token);
//        if (token==null){
//            token=(String) redisTemplate.opsForValue().get("LOGIN_TOKEN_ON");
//        }
        System.out.println(session.getId());
        User user=null;
        if (token!=null){
              user= (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
        }
        if (user!=null){
            isLogin=true;
        }
        System.out.println("程序运行时间111： "+(endTime-startTime)+"ms");
        startTime=System.currentTimeMillis();   //获取开始时间
        List<ItemCat> itemCats1=itemService.getHomePageList();
        System.out.println(web);
        endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间222： "+(endTime-startTime)+"ms");


        List<Item> likes= (List<Item>) session.getAttribute("like");

        model.addAttribute("itemCats",itemCats);
        model.addAttribute("menu",menuServicel.getMenulistAndOn());
        model.addAttribute("ppt",homepptService.getHomepptlistAndOn());
        model.addAttribute("recomm",itemService.getItemListByRecommended());
        model.addAttribute("itemCatstree",itemCatstree);
        model.addAttribute("hot2",itemService.getItemListByHot());
        model.addAttribute("new2",itemService.getItemListByNews());
        model.addAttribute("homeitem",itemCats1);
        model.addAttribute("likes",likes);
        model.addAttribute("isLogin",isLogin);
        model.addAttribute("user",user);
        model.addAttribute("itemurl",ITEM_URL);
        model.addAttribute("ssourl",SSO_URL);
        model.addAttribute("homeurl",HOME_URL);
        model.addAttribute("orderurl",ORDER_URL);
        model.addAttribute("userurl",USER_URL);
        System.out.println(user);
//        for (ItemCat i:itemCats1
//             ) {
//            System.out.println(i);
//        }
        return  "index";
    }

    @ResponseBody
    @RequestMapping("/getlist")
    public List<ItemCat> getlist(){
        long startTime=System.currentTimeMillis();   //获取开始时间
        List<ItemCat> itemCats1=itemService.getHomePageList();
        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return  itemCats1;
    }
}
