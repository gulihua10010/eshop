package cn.jianwoo.eshop.webconfig.service.impl;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Homeppt;
import cn.jianwoo.eshop.manage.entity.Menu;
import cn.jianwoo.eshop.manage.mapper.MenuMapper;
import cn.jianwoo.eshop.webconfig.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public EShopResult insert(Menu menu) {
        if (menu!=null){
            Integer max=menuMapper.max();
            menu.setOrder(max+1);
//            menu.setStatus(1);
            menuMapper.insert(menu);

            redisTemplate.opsForValue().set("Menu_all",menuMapper.getMenulist());
            redisTemplate.opsForValue().set("Menu_all_ON",menuMapper.getMenulistAndOn());
            redisTemplate.expire("Menu_all",24,TimeUnit.HOURS);
            redisTemplate.expire("Menu_all_ON",24,TimeUnit.HOURS);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Override
    public EShopResult update(Menu menu) {
        if (menu!=null){
            menuMapper.update(menu);
            redisTemplate.opsForValue().set("Menu_all",menuMapper.getMenulist());
            redisTemplate.opsForValue().set("Menu_all_ON",menuMapper.getMenulistAndOn());
            redisTemplate.expire("Menu_all",24,TimeUnit.HOURS);
            redisTemplate.expire("Menu_all_ON",24,TimeUnit.HOURS);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Override
    public EShopResult delete(Menu menu) {
        if (menu!=null){
            menu.setStatus(1);
            menuMapper.delete(menu);
            redisTemplate.opsForValue().set("Menu_all",menuMapper.getMenulist());
            redisTemplate.opsForValue().set("Menu_all_ON",menuMapper.getMenulistAndOn());
            redisTemplate.expire("Menu_all",24,TimeUnit.HOURS);
            redisTemplate.expire("Menu_all_ON",24,TimeUnit.HOURS);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Override
    public Integer count() {
        return menuMapper.count();
    }

    @Override
    public List<Menu> getMenulist() {
        try {
            List<Menu> menus= (List<Menu>) redisTemplate.opsForValue().get("Menu_all");
            if (menus!=null){
                return  menus;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Menu> menus=menuMapper.getMenulist();
        redisTemplate.opsForValue().set("Menu_all",menus);
        redisTemplate.expire("Menu_all",24,TimeUnit.HOURS);
        return menus;
    }

    @Override
    public List<Menu> getMenulistAndOn() {
        try {
            List<Menu> menus= (List<Menu>) redisTemplate.opsForValue().get("Menu_all_ON");
            if (menus!=null){
                return  menus;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Menu> menus=menuMapper.getMenulistAndOn();
        redisTemplate.opsForValue().set("Menu_all_ON",menus);
        redisTemplate.expire("Menu_all_ON",24,TimeUnit.HOURS);
        return menus;
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.getMenuById(id);
    }
}
