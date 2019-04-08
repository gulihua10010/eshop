package cn.jianwoo.eshop.manage.mapper;

import cn.jianwoo.eshop.manage.entity.ItemImages;
import cn.jianwoo.eshop.manage.entity.ItemProv;

import java.util.List;
import java.util.Map;

public interface ItemProvMapper {

    int insert(ItemProv itemProv);
    int update(ItemProv itemProv);
    int delete(ItemProv itemProv);
    int deleteByIid(Long iid);
    List<ItemProv> getItemProv(Map<String, Object> params) ;
    ItemProv getItemProvById(Long id) ;
    List<ItemProv>  getItemProvByIid(Long Iid) ;
}