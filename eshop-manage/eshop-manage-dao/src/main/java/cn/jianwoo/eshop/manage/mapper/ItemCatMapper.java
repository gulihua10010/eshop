package cn.jianwoo.eshop.manage.mapper;


import cn.jianwoo.eshop.manage.entity.ItemCat;

import java.util.List;

public interface ItemCatMapper {
    List<ItemCat> getItemCatByParentId(Long parentId);
    List<ItemCat> getItemCatBychild();
    int insert(ItemCat itemCat);
    int update(ItemCat itemCat);
    int delete(ItemCat itemCat);
    int deletebyParent(ItemCat itemCat);
    ItemCat getItemCatNameById(Long id);
}
