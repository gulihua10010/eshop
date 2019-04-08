package cn.jianwoo.eshop.item.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.ItemImages;

import java.util.List;
import java.util.Map;

public interface ItemImagesService {
    EShopResult insert(ItemImages itemImages);
    EShopResult update(ItemImages itemImages);
    EShopResult delete(ItemImages itemImages);
    ItemImages getItemImagesById(Long id) ;
    List<ItemImages> getItemImagesByIid(Long Iid) ;
}
