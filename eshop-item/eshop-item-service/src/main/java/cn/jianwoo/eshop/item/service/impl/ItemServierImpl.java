package cn.jianwoo.eshop.item.service.impl;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.common.utils.IDUtils;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.entity.ItemCat;
import cn.jianwoo.eshop.manage.entity.ItemImages;
import cn.jianwoo.eshop.manage.entity.ItemProv;
import cn.jianwoo.eshop.manage.mapper.ItemCatMapper;
import cn.jianwoo.eshop.manage.mapper.ItemImagesMapper;
import cn.jianwoo.eshop.manage.mapper.ItemMapper;
import cn.jianwoo.eshop.manage.mapper.ItemProvMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ItemServierImpl implements ItemService {
    private static  final Logger log=LoggerFactory.getLogger(ItemServierImpl.class);

    @Autowired
    ItemMapper itemMapper;
    @Autowired
    ItemCatMapper itemCatMapper;
    @Autowired
    ItemImagesMapper itemImagesMapper;
    @Autowired
    ItemProvMapper itemProvMapper;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Value("${ITEM_INFO_KEY}")
    private String ITEM_INFO_KEY ;

    @Value("${ITEM_INFO_EXPIRE}")
    private Integer ITEM_INFO_EXPIRE;

    @Override
    public Item getById(Long id) {

        try {
            Item item = (Item) redisTemplate.opsForValue().get(ITEM_INFO_KEY + ":" + id);
            if (item != null) {
                log.info("read reids  item base ..");
//                System.out.println("read reids  item base ..");
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Item item = itemMapper.getById(id);
        if (item != null) {
            redisTemplate.opsForValue().set(ITEM_INFO_KEY + ":" + id, item);
            redisTemplate.expire(ITEM_INFO_KEY + ":" + id, ITEM_INFO_EXPIRE,TimeUnit.HOURS);
            System.out.println("redis save item...");
            log.info("redis save item...");

            return  item;
        }
        return null;
    }

    @Override
    public List<Item> getItemListByHot() {
        return itemMapper.getItemListByHot();
    }

    @Override
    public List<Item> getItemListByNews() {
        return itemMapper.getItemListByNews();
    }

    @Override
    public List<Item> getItemListByNewsByn(Integer n) {
        return itemMapper.getItemListByNewsByn(n);
    }

    @Override
    public List<Item> getItemList() {
        return itemMapper.getItemList();
    }

    @Override
    public LayuiResult getItemListByPage(int page, int rows,String kw) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        List<Item> list=null;
        if (kw==null){
            list = itemMapper.getItemList();
        }else{
        Map<String,Object> param=new HashMap<>();
        param.put("type","liketitle");
        param.put("params",kw);
            list = itemMapper.getItemListByMap(param);
        }
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        return         LayuiResult.ok(pageInfo.getTotal(),list) ;
    }

    @Override
    public LayuiResult getItemListByyRecommendedWithPage(int page, int rows, String kw) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        List<Item> list=null;
        if (kw==null){
            list = itemMapper.getItemListByRecommended();
        }else{
            Map<String,Object> param=new HashMap<>();
            param.put("type","liketitle");
            param.put("params",kw);
            list = itemMapper.getItemListByRecommendedWithMap (param);
        }
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        return         LayuiResult.ok(pageInfo.getTotal(),list) ;
    }

    @Override
    public List<Item> getItemListByMap(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<Item> getItemListByMaps(Map<String, Object> maps) {
        return itemMapper.getItemListByMaps(maps);
    }

    @Override
    public Integer getcount() {
        return  itemMapper.getcount();
    }

    /**
     * 后台管理添加商品至数据库
     *
     * @param item 商品
     * @return
     */

    @Transactional
    @Override
    public EShopResult addItem(Item item) {
        if (item != null) {

            // 1、生成商品id
            long itemId = IDUtils.genItemId();
            // 2、补全TbItem对象的属性
            item.setId(itemId);
            //商品状态，1-正常，2-下架，3-删除
            item.setStatus(1);
            item.setCreated(new Timestamp(new Date().getTime()));
            item.setUpdated(new Timestamp(new Date().getTime()));
            item.setMonSales(0L);
            List<ItemImages> images=item.getItemImages();
            List<ItemProv> provs=item.getItemProvs();
            for (ItemImages i: images) {
                i.setItemId(itemId);
                itemImagesMapper.insert(i);
            }
            for (ItemProv i: provs) {
                i.setItemId(itemId);
                itemProvMapper.insert(i);
            }
            // 3、向商品表插入数据
            itemMapper.insert(item);
            // 4、发送消息队列，通知新增商品id
            ActiveMQTopic itemAddTopic = new ActiveMQTopic("itemAddTopic");
            jmsMessagingTemplate.convertAndSend(itemAddTopic, item.getId());
            return EShopResult.ok();


        } else {
            return EShopResult.error("商品不能为空");
        }
    }

    @Transactional
    @Override
    public EShopResult updateItem(Item item) {
        if (item != null && item.getId() != null) {
            item.setCreated(new Timestamp(new Date().getTime()));
            List<ItemImages> images=item.getItemImages();
            List<ItemProv> provs=item.getItemProvs();
            itemImagesMapper.deleteByIid(item.getId());
            itemProvMapper.deleteByIid(item.getId());
            if (images!=null&&images.size()>0){
                for (ItemImages i: images) {
                    itemImagesMapper.insert(i);
                }
            }
            if (provs!=null&&provs.size()>0){

                for (ItemProv i: provs) {
                itemProvMapper.insert(i);
             }
            }
            itemMapper.update(item);

            redisTemplate.opsForValue().set(ITEM_INFO_KEY + ":" + item.getId(),  itemMapper.getById(item.getId()));
            redisTemplate.expire(ITEM_INFO_KEY + ":" + item.getId(), ITEM_INFO_EXPIRE,TimeUnit.HOURS);
            System.out.println("redis save item...");
            return EShopResult.ok();

        } else {
            return EShopResult.error("商品不能为空");
        }
    }

    @Override
    public EShopResult delItem(Item item) {
        if (item!=null||item.getId()!=null){
            itemMapper.delete(item);
            return  EShopResult.ok();
        }else{
            return  EShopResult.error();

        }
    }

    @Override
    public Integer countRecommended() {
        return itemMapper.countRecommended();
    }

    @Override
    public List<Item> getItemListByRecommended() {
        return itemMapper.getItemListByRecommended();
    }

    @Override
    public List<Item> getItemListByCatLimitByn(Long cid,Integer n,String order) {
        Map<String ,Object> map=new HashMap<>();
        map.put("cid",cid);
        map.put("n",n);
        map.put("order",order);
        return itemMapper.getItemListByCatLimitByn(map);
    }



    @Override
    public List<ItemCat> getHomePageList() {
        List<ItemCat> itemCatsRedis= (List<ItemCat>) redisTemplate.opsForValue().get("HOME_LIST_ITEM");
        if(itemCatsRedis!=null){
            return  itemCatsRedis;
        }
        List<ItemCat> itemCats=itemCatMapper.getItemCatByParentId(0L);
        for (ItemCat i:itemCats ) {
            System.out.println(i.getId());
            i.setItems(getItemListByCatLimitByn(i.getId(),8,"random_id"));

            List<ItemCat> itemCats1=itemCatMapper.getItemCatByParentId(i.getId());
            i.setItemCats(itemCats1);
            for (ItemCat i1:itemCats1 ) {
                i1.setItems(getItemListByCatLimitByn(i1.getId(),10,"random_id"));
            }
        }
        redisTemplate.opsForValue().set("HOME_LIST_ITEM",itemCats);
        redisTemplate.expire("HOME_LIST_ITEM",72,TimeUnit.HOURS);


        return itemCats;
    }
}
