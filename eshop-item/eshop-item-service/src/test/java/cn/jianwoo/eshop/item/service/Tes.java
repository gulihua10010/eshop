package cn.jianwoo.eshop.item.service;

import cn.jianwoo.eshop.common.utils.IDUtils;
import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.item.service.impl.ItemServierImpl;
import cn.jianwoo.eshop.manage.entity.ItemCat;
import cn.jianwoo.eshop.manage.entity.ItemComment;
import cn.jianwoo.eshop.manage.entity.ItemImages;
import cn.jianwoo.eshop.manage.entity.ItemProv;
import cn.jianwoo.eshop.manage.mapper.ItemCommentMapper;
import cn.jianwoo.eshop.manage.mapper.ItemImagesMapper;
import cn.jianwoo.eshop.manage.mapper.ItemMapper;
import cn.jianwoo.eshop.manage.mapper.ItemProvMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

//import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tes {
//    @Autowired
//    ItemCatService itemServier;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    ItemImagesMapper itemImagesMapper;
    @Autowired
    ItemProvMapper itemProvMapper;
    @Autowired
    ItemCommentMapper itemCommentMapper;
    @Autowired
    ItemMapper itemMapper;
    @Test
    public void test(){
        redisTemplate.opsForValue().set( "ddddd:"  , "ddddddddd");

//       List<ItemCatLayuiTree> itemCatLayuiTrees= itemServier.getItemCatListTree();
//        System.out.println(itemCatLayuiTrees);
//        ItemCatLayuiTree itemCatLayuiTree=new ItemCatLayuiTree();
//        ItemCatLayuiTree itemCatLayuiTree1=new ItemCatLayuiTree();
//        List<ItemCatLayuiTree> itemCatLayuiTrees=new ArrayList<>();
//        itemCatLayuiTree.setName("d");
//        itemCatLayuiTrees.add(itemCatLayuiTree);
//        itemCatLayuiTree1.setChildren(itemCatLayuiTrees);
//        itemCatLayuiTree.setName("c");
//        System.out.println(itemCatLayuiTree1);

    }
@Test
    public  void image(){
ItemImages itemImages=new ItemImages(1L,3L,"dd");
//itemImagesMapper.update(itemImages);
//itemImagesMapper.getItemImagesById(1L);
//itemImagesMapper.getItemImagesByIid(1L);
//    itemImagesMapper.delete(itemImages);
    System.out.println(itemMapper.getById(155333351273343L));

}
    @Test
    public  void prov(){
        ItemProv itemProv=new ItemProv(1L,1L,"dd","d");
//        itemProvMapper.update(itemProv);
//        itemProvMapper.getItemProvById(1L);
//        itemProvMapper.getItemProvByIid(1L);
        itemProvMapper.delete(itemProv);
    }

    @Test
    public  void comment(){
        ItemComment itemComment=new ItemComment(1L,1L,1L,1,"dd",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));
//        itemCommentMapper.update(itemComment);
//        itemCommentMapper.getItemCommentById(1L);
//        itemCommentMapper.getItemCommentByIid(1L);
        itemCommentMapper.delete(itemComment);
    }

    @Test
    public void ttt(){

        String s=IDUtils.randomChar(10);
        System.out.println(s);
    }
}

