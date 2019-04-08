package cn.jianwoo.eshop.item.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.response.LayuiResult;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.entity.ItemCat;

import java.util.List;
import java.util.Map;

public interface ItemService {
  public   Item getById(Long id);

  List<Item> getItemListByHot();
  List<Item> getItemListByNews();
  List<Item> getItemListByNewsByn(Integer n);
    public List<Item> getItemList();
    public LayuiResult getItemListByPage(int page, int rows,String kw);
    public LayuiResult getItemListByyRecommendedWithPage(int page, int rows,String kw);
    public List<Item> getItemListByMap(Map<String, Object> params);
  List<Item> getItemListByMaps(Map<String,Object> maps);
  Integer getcount();

  public EShopResult addItem(Item item);
    public EShopResult updateItem(Item item);
    public EShopResult delItem(Item item);
  Integer countRecommended();

  List<Item> getItemListByRecommended();
  List<Item> getItemListByCatLimitByn(Long cid,Integer n,String order);

  List<ItemCat > getHomePageList();
}
