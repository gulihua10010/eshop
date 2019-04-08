package cn.jianwoo.eshop.item.web.controller;

import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.entity.ItemCat;
import cn.jianwoo.eshop.manage.entity.User;
import cn.jianwoo.eshop.manage.entity.WebConfig;
import cn.jianwoo.eshop.webconfig.api.HomepptService;
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
    ItemCatService itemCatService;
    @Autowired
    MenuService menuServicel;
    @Autowired
    HomepptService homepptService;
    @Autowired
    ItemService itemService;
    @Autowired
    WebConfigService webConfigService;
    private  boolean isLogin=false;

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${SSO_URL}")
    private  String SSO_URL;
    @Value("${ITEM_URL}")
    private  String ITEM_URL;
    @Value("${HOME_URL}")
    private  String HOME_URL;
    @Value("${USER_URL}")
    private  String USER_URL;

    private  static  final  String prix="/item";
@Value("${ORDER_URL}")
    private  String ORDER_URL;

    @RequestMapping(prix+"/detail")
    public  String detail(Model model, Long id, HttpServletRequest request){
        HttpSession session=request.getSession();
//验证是否登录
        String token= (String) session.getAttribute("LOGIN_TOKEN_ON");
//        if (token==null){
//            token=(String) redisTemplate.opsForValue().get("LOGIN_TOKEN_ON");
//        }
        System.out.println(isLogin);
        System.out.println("toekn "+token);
        System.out.println(session.getId() );
        User user=null;
        if (token!=null) {
            user = (User) redisTemplate.opsForValue().get("REDIS_SESSION_KEY_LOGIN" + ":" + token);
        }
        System.out.println(user!=null);

        if (user!=null){
            isLogin=true;
        }else {
            isLogin=false;
        }
        System.out.println(user!=null);
        System.out.println(user);
        System.out.println(isLogin);
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



        session.setAttribute("webconfig",web);
        model.addAttribute("webconfig",web);


//greatview
        Item item=itemService.getById(id);
        System.out.println(id);
        ItemCat itemCat1=null;
        ItemCat itemCat2=null;
        ItemCat itemCat3=null;
        System.out.println(item);
          itemCat1=itemCatService.getItemCatNameById(item.getCid());
        System.out.println(itemCat1);
          itemCat1.setItemCats(itemCatService.getItemCatByParentId(itemCat1.getParentId()));
        System.out.println(itemCat1.getParentId());
          if (itemCat1!=null&&itemCat1.getParentId()!=0){
              itemCat2=itemCatService.getItemCatNameById(itemCat1.getParentId());
              if (itemCat2!=null){
                  itemCat2.setItemCats(itemCatService.getItemCatByParentId(itemCat2.getParentId()));

              }

          }
        if (itemCat2!=null&&itemCat2.getParentId()!=0){
            itemCat3=itemCatService.getItemCatNameById(itemCat2.getParentId());
            if (itemCat3!=null){
                itemCat3.setItemCats(itemCatService.getItemCatByParentId(itemCat3.getParentId()));

            }
        }
//        System.out.println(itemCat1);
//        System.out.println(itemCat2);
//        System.out.println(itemCat3);
//Map<String,Object> hot=new HashMap<>();
//hot.put("cid",item.getCid());
//hot.put("n",8);
//hot.put("order","mon_sales");

        List<Item> likes= (List<Item>) session.getAttribute("like");
        if (likes==null){
            likes=new ArrayList<>();
            likes.add(item);
        }else{
            likes.add(item);
        }

        session.setAttribute( "like",likes);


        List<Item> hot=  itemService.getItemListByCatLimitByn(itemCat1.getParentId(),2,"mon_sales");
        List<Item> see=  itemService.getItemListByCatLimitByn(itemCat1.getParentId(),6,"random_id");
        List<Item> like=  itemService.getItemListByCatLimitByn(itemCat1.getParentId(),3,"id");
        model.addAttribute("itemCats",itemCats);
        model.addAttribute("cat1",itemCat1);
        model.addAttribute("cat2",itemCat2);
        model.addAttribute("cat3",itemCat3);
        model.addAttribute("menu",menuServicel.getMenulistAndOn());
        model.addAttribute("itemCatstree",itemCatstree);
        model.addAttribute("item",item);
        System.out.println(item);
        model.addAttribute("hot",hot);
        model.addAttribute("like",like);
        model.addAttribute("see",see);
        model.addAttribute("see",see);
        model.addAttribute("ssourl",SSO_URL);
        model.addAttribute("homeurl",HOME_URL);
        model.addAttribute("homeurl",HOME_URL);
        model.addAttribute("itemurl",ITEM_URL);
        model.addAttribute("orderurl",ORDER_URL);

        model.addAttribute("isLogin",isLogin);
        model.addAttribute("user",user);
        System.out.println(hot.size());
    return  "detail";
    }
}
