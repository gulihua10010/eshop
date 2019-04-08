package cn.jianwoo.eshop.webconfig.service;

import cn.jianwoo.eshop.manage.entity.Homeppt;
import cn.jianwoo.eshop.manage.entity.Menu;
import cn.jianwoo.eshop.manage.entity.WebConfig;
import cn.jianwoo.eshop.manage.mapper.HomepptMapper;
import cn.jianwoo.eshop.manage.mapper.MenuMapper;
import cn.jianwoo.eshop.manage.mapper.WebConfigMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Te {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    WebConfigMapper webConfigMapper;
    @Autowired
    HomepptMapper homepptMapper;
    @Test
    public void menu1(){
//        Menu menu=new Menu(1,"dd,",1,"d");
//        menuMapper.delete(menu);
        System.out.println(menuMapper.count());
        System.out.println(menuMapper.max());
//        System.out.println(menuMapper.getMenulistAndOn());
//        System.out.println(menuMapper.getMenulist());

    }
    @Test
    public  void ppt(){
        Homeppt homeppt=new Homeppt(1,"d","d",1,"");

        homepptMapper.delete(homeppt);
        homepptMapper.getHomepptById(1);
//        osut qhuiying
        homepptMapper.getHomepptlist();
        homepptMapper.getHomepptlistAndOn();
    }

    @Test
    public void web(){
        WebConfig webConfig=new WebConfig(1,",","d",1);
        webConfigMapper.delete(webConfig);
        webConfigMapper.getWebConfigById(1);
        webConfigMapper.geWebConfiglistAndOn();
        webConfigMapper.geWebConfiglist();

    }
}
