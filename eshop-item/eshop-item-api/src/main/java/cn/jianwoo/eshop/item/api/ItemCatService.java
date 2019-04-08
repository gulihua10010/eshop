package cn.jianwoo.eshop.item.api;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.common.utils.ItemCatLayuiTree;
import cn.jianwoo.eshop.manage.entity.ItemCat;

import java.util.List;

public interface ItemCatService {
    List<ItemCat> getItemCatByParentId(Long parentId);
    List<ItemCatLayuiTree> getItemCatListTree();
    EShopResult  insert(ItemCat itemCat);
    EShopResult delete(ItemCat itemCat);
    EShopResult update(ItemCat itemCat);
    ItemCat getItemCatNameById(Long id);
}
