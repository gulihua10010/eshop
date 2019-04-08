package cn.jianwoo.eshop.item.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.ItemProv;

import java.util.List;
import java.util.Map;

public interface ItemProvService {
    EShopResult insert(ItemProv itemProv);
    EShopResult update(ItemProv itemProv);
    EShopResult delete(ItemProv itemProv);
    List<ItemProv> getItemProv(Map<String, Object> params) ;
    ItemProv getItemProvById(Long id) ;
    List<ItemProv>  getItemProvByIid(Long Iid) ;

}
