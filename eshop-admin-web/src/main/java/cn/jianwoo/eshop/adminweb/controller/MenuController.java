package cn.jianwoo.eshop.adminweb.controller;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Homeppt;
import cn.jianwoo.eshop.manage.entity.Menu;
import cn.jianwoo.eshop.webconfig.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {
    @Autowired
    MenuService menuService;
    @RequestMapping("/savemenu")
    public EShopResult addmenu(@RequestBody Menu menu){
        System.out.println(menu);
    return  menuService.insert(menu);
    }
    @RequestMapping("/updatemenu")
    public EShopResult updatemenu(@RequestBody Menu menu,Integer id){
        menu.setId(id);
        System.out.println(menu);

        return  menuService.update(menu);
    }
    @RequestMapping("/delmenu")
    public EShopResult delmenu(Integer id){
        Menu menu=new Menu();
        menu.setId(id);
        return  menuService.delete(menu);
    }
    @RequestMapping("/sortmenu")
    public EShopResult sortmenu(@RequestBody  List<Integer> data) {
        System.out.println(data);
        Integer j=1;
        for (Integer i:data){
            Menu menu=new Menu();
            menu.setId(i);
            menu.setOrder(j++);
            menuService.update(menu);
        }
        return  EShopResult.ok();
    }

}
