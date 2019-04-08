package cn.jianwoo.eshop.item.service.impl;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.item.api.ItemProvService;
import cn.jianwoo.eshop.manage.entity.ItemProv;
import cn.jianwoo.eshop.manage.mapper.ItemProvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public class ItemProvServiceImpl implements ItemProvService {
    @Autowired
    ItemProvMapper itemProvMapper;

    @Transactional
    @Override
    public EShopResult insert(ItemProv itemProv) {
        if (itemProv!=null&&itemProv.getItemId()!=null){
            itemProvMapper.insert(itemProv);
            return  EShopResult.ok();
        }
        return EShopResult.error();
    }
@Transactional
    @Override
    public EShopResult update(ItemProv itemProv) {
    if (itemProv!=null&&itemProv.getItemId()!=null){
        itemProvMapper.update(itemProv);
        return  EShopResult.ok();
    }
    return EShopResult.error();
    }
    @Transactional
    @Override
    public EShopResult delete(ItemProv itemProv) {
        if (itemProv!=null&&itemProv.getItemId()!=null){
            itemProvMapper.delete(itemProv);
            return  EShopResult.ok();
        }
        return EShopResult.error();
    }

    @Override
    public List<ItemProv> getItemProv(Map<String, Object> params) {
        return null;
    }

    @Override
    public ItemProv getItemProvById(Long id) {
        return itemProvMapper.getItemProvById(id);
    }

    @Override
    public List<ItemProv> getItemProvByIid(Long Iid) {
        return itemProvMapper.getItemProvByIid(Iid);
    }
}
