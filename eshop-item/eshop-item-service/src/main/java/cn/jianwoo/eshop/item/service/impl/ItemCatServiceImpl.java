package cn.jianwoo.eshop.item.service.impl;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.manage.entity.ItemCat;
import cn.jianwoo.eshop.manage.mapper.ItemCatMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//@Service(protocol = {"dubbo"},version = "1.0" ,timeout = 3000,validation = "true")
@Component
@org.springframework.stereotype.Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    ItemCatMapper itemCatMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Value("${ITEM_INFO_KEY}")
    private String ITEM_INFO_KEY ;
    @Value("${ITEM_CAT_INFO_KEY_ALL}")
    private  String ITEM_CAT_INFO_KEY_ALL;
    ItemCatLayuiTree itemCatLayuiTree;

    @Value("${ITEM_INFO_EXPIRE}")
    private Integer ITEM_INFO_EXPIRE;
    @Override
    public List<ItemCat> getItemCatByParentId(Long parentId) {
        return itemCatMapper.getItemCatByParentId(parentId);
    }

    @Override
    public List<ItemCatLayuiTree> getItemCatListTree() {
        long startTime=System.currentTimeMillis();   //获取开始时间
        List<ItemCatLayuiTree> itemCatLayuiTree= (  List<ItemCatLayuiTree>) redisTemplate.opsForValue().get(ITEM_CAT_INFO_KEY_ALL);
        if (itemCatLayuiTree==null){
            List<ItemCatLayuiTree> layuiTrees=  getItemCatListTreeByParent(0L);
            System.out.println(layuiTrees);
            redisTemplate.opsForValue().set(ITEM_CAT_INFO_KEY_ALL , layuiTrees);
            redisTemplate.expire(ITEM_CAT_INFO_KEY_ALL, ITEM_INFO_EXPIRE,TimeUnit.HOURS);
            return  layuiTrees;
        }
//        List<ItemCatLayuiTree> layuiTrees=  getItemCatListTreeByParent(0L);
//
        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
        return itemCatLayuiTree;
    }
    private   List<ItemCatLayuiTree> getItemCatListTreeByParent(Long parentId){

        List<ItemCat> itemCats=itemCatMapper.getItemCatByParentId(parentId);
        List<ItemCatLayuiTree> itemCatLayuiTrees1=new ArrayList<>();
        itemCatLayuiTree=new ItemCatLayuiTree();
//        ItemCatLayuiTree
        if (itemCats.size()>0){
            for (ItemCat itemCat:itemCats){
                List<ItemCatLayuiTree> itemCatLayuiTreess=   getItemCatListTreeByParent(itemCat.getId());//children
                itemCatLayuiTree.setId(itemCat.getId());
                itemCatLayuiTree.setName(itemCat.getName());
                itemCatLayuiTree.setSpread(false);
                itemCatLayuiTree.setChildren(itemCatLayuiTreess);
                itemCatLayuiTrees1.add(itemCatLayuiTree);
                itemCatLayuiTree=new ItemCatLayuiTree();
            }

            return  itemCatLayuiTrees1;
        }
        return  null;

    }
    private     List<ItemCat> getItemCatByParentId(List<ItemCat> itemCats,Long parentId){
        List<ItemCat> itemCats1=null;
        final List<Long> plist = new ArrayList<Long>();
        plist.add(parentId);

        itemCats1=itemCats.stream().filter((ItemCat ic)->plist.contains(ic.getParentId())).collect(Collectors.toList());
        return  itemCats1;

    }

    @Transactional
    @Override
    public EShopResult insert(ItemCat itemCat) {
        System.out.println(itemCat);
        if (itemCat!=null){
            itemCat.setStatus(1);
            itemCat.setSortOrder(1);
            itemCat.setCreated(new Timestamp(new Date().getTime()));

            itemCatMapper.insert(itemCat);

            List<ItemCatLayuiTree> layuiTrees=  getItemCatListTreeByParent(0L);
            System.out.println(layuiTrees);
            redisTemplate.opsForValue().set(ITEM_CAT_INFO_KEY_ALL , layuiTrees);
            redisTemplate.expire(ITEM_CAT_INFO_KEY_ALL, ITEM_INFO_EXPIRE,TimeUnit.HOURS);
            return  EShopResult.ok(itemCat);
        }else{
            return  EShopResult.error("类别不能为空");
        }
    }

    @Transactional
    @Override
    public EShopResult delete(ItemCat itemCat) {
        if (itemCat==null){
            return  EShopResult.error();
        }
         itemCatMapper.delete(itemCat);
        List<ItemCatLayuiTree> layuiTrees=  getItemCatListTreeByParent(0L);
        System.out.println(layuiTrees);
        redisTemplate.opsForValue().set(ITEM_CAT_INFO_KEY_ALL , layuiTrees);
        redisTemplate.expire(ITEM_CAT_INFO_KEY_ALL, ITEM_INFO_EXPIRE,TimeUnit.HOURS);
        return EShopResult.ok();
    }

    @Override
    public EShopResult update(ItemCat itemCat) {
        if (itemCat!=null){
            itemCat.setStatus(1);
            itemCatMapper.update(itemCat);
            List<ItemCatLayuiTree> layuiTrees=  getItemCatListTreeByParent(0L);
            System.out.println(layuiTrees);
            redisTemplate.opsForValue().set(ITEM_CAT_INFO_KEY_ALL , layuiTrees);
            redisTemplate.expire(ITEM_CAT_INFO_KEY_ALL, ITEM_INFO_EXPIRE,TimeUnit.HOURS);
            return  EShopResult.ok();
        }else{
            return  EShopResult.error("类别不能为空");
        }
    }

    @Override
    public ItemCat getItemCatNameById(Long id) {
        return itemCatMapper.getItemCatNameById(id);
    }
}
