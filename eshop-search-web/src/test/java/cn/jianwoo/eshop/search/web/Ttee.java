package cn.jianwoo.eshop.search.web;

import cn.jianwoo.eshop.api.SearchItemService;
import cn.jianwoo.eshop.api.SearchService;
import cn.jianwoo.eshop.common.response.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Ttee {
    @Autowired
    SearchService searchService;
    @Autowired
    SearchItemService searchItemService;
    @Test
    public void  ss(){
//
        try {
          SearchResult result= searchService.search("水果",1,20);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
//searchItemService.importItems();

    }

}
