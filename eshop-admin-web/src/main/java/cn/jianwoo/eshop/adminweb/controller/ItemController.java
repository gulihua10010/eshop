package cn.jianwoo.eshop.adminweb.controller;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.item.api.ItemCatService;
import cn.jianwoo.eshop.item.api.ItemService;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.entity.ItemCat;
import cn.jianwoo.eshop.manage.entity.ItemImages;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemCatService itemCatService;
    @Autowired
    ItemService itemService;
    @GetMapping("/Item/getItemCatlistByTree")
    public List<ItemCatLayuiTree> getItemCatlistByTree(){
    return  itemCatService.getItemCatListTree();
    }
    @PostMapping("/Item/addItemCat")
    public EShopResult addItemCat(String name,Long pid){
        if(name==null||name==""){
            return  EShopResult.error("名字不能为空");
        }
        ItemCat itemCat=new ItemCat();
        itemCat.setName(name);
        itemCat.setParentId(pid);
        System.out.println(itemCat);
        return  itemCatService.insert(itemCat);
    }
    @PostMapping("/Item/editItemCat")
    public EShopResult editItemCat(String name,Long id){
        if(name==null||name==""){
            return  EShopResult.error("名字不能为空");
        }
        ItemCat itemCat=new ItemCat();
        itemCat.setId(id);
        itemCat.setName(name);
        return  itemCatService.update(itemCat);
    }
    @PostMapping("/Item/delItemCat")
    public EShopResult delItemCat(Long id){
        ItemCat itemCat=new ItemCat();
        itemCat.setId(id);
        System.out.println(itemCat);
        return  itemCatService.delete(itemCat);
    }
    @PostMapping("/Item/delSelectItemCat")
    public EShopResult delSelectItemCat(@RequestBody List<ItemCatLayuiTree> itemCatLayuiTrees){
        ItemCat itemCat;
//        List<ItemCat> itemCats=new ArrayList<>();
        for (ItemCatLayuiTree i :itemCatLayuiTrees  ) {
            itemCat=new ItemCat();
            itemCat.setId(i.getId());
            itemCatService.delete(itemCat);
        }
        System.out.println(itemCatLayuiTrees);
        return  EShopResult.ok();
    }

    @GetMapping("/Item/getItemList")
    public LayuiResult getItemList(Integer page,Integer limit,String kw){

        LayuiResult pageInfo=itemService.getItemListByPage(page,limit,kw);
        System.out.println(pageInfo);
        return  pageInfo;
    }
    @GetMapping("/Item/getItemWithRecommList")
    public LayuiResult getItemWithRecommList(Integer page,Integer limit,String kw){

        LayuiResult pageInfo=itemService.getItemListByyRecommendedWithPage(page,limit,kw);
        System.out.println(pageInfo);
        return  pageInfo;
    }

    @PostMapping("/Item/editItem")
    public EShopResult editItem(@RequestBody Item item){
        System.out.println(item);

        EShopResult eShopResult=itemService.updateItem(item);
        return  eShopResult;
    }
    @PostMapping("/Item/addItem")
    public EShopResult addItem(@RequestBody Item item){
        System.out.println("adaddaddadddd");
        System.out.println(item);
        EShopResult eShopResult=itemService.addItem(item);
        return  eShopResult;
    }
    @GetMapping("/Item/delItem")
    public EShopResult delItem(Long id){
        Item item=new Item();
        item.setId(id);
        EShopResult eShopResult=itemService.delItem(item);
        return  eShopResult;
    }

    @GetMapping("/Item/updatestatus")
    public EShopResult updatestatus(Long id,Integer status){
        Item item=new Item();
        item.setId(id);
        item.setStatus(status);
        EShopResult eShopResult=itemService.updateItem(item);
        return  eShopResult;
    }
    @GetMapping("/Item/recommended")
    public EShopResult recommended(Long id,Integer recommended){
        Item item=new Item();
        item.setId(id);
        item.setRecommended(recommended);
        Integer countRcomm=itemService.countRecommended();
        System.out.println(countRcomm);
        if (recommended==1&&countRcomm>8){
            return  EShopResult.error("首页推荐商品不可多于8个");
        }
        EShopResult eShopResult=itemService.updateItem(item);
        return  eShopResult;
    }
    @PostMapping("/Item/updateStatusSelectItem")
    public EShopResult updateStatusSelectItem(@RequestBody List<Item> items,Integer type){
        if (type==1){
            for (Item i :items  ) {
                Item item=new Item();
                item.setId(i.getId());
                item.setStatus(0);
                itemService.updateItem(item);
            }

        }else if (type==2){
            for (Item i :items  ) {
                Item item=new Item();
                item.setId(i.getId());
                item.setStatus(1);
                itemService.updateItem(item);
            }

        }

        return  EShopResult.ok();
    }
    @PostMapping("/updatecatpic")
    public EShopResult updatecatpic(@RequestBody List<ItemCat> itemCats){
        System.out.println(itemCats);
        for (ItemCat i:itemCats){
            itemCatService.update(i);
        }
        return  EShopResult.ok();
    }

    @GetMapping("/getnewitems")
    public LayuiResult getnewitems( ){
        List<Item> items=itemService.getItemListByNewsByn(10);
        return  LayuiResult.ok(10L,items);
    }
}
