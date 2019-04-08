package cn.jianwoo.eshop.item.service.impl;

import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.item.api.ItemImagesService;
import cn.jianwoo.eshop.manage.entity.ItemImages;
import cn.jianwoo.eshop.manage.mapper.ItemImagesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ItemImagesServiceImpl implements ItemImagesService {
    @Autowired
    ItemImagesMapper itemImagesMapper;

    @Transactional
    @Override
    public EShopResult insert(ItemImages itemImages) {
        if (itemImages!=null&&itemImages.getItemId()!=null){
            itemImagesMapper.insert(itemImages);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Transactional
    @Override
    public EShopResult update(ItemImages itemImages) {
        if (itemImages!=null&&itemImages.getId()!=null){
            itemImagesMapper.update(itemImages);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Override
    public EShopResult delete(ItemImages itemImages) {
        if (itemImages!=null&&itemImages.getId()!=null){
            itemImagesMapper.delete(itemImages);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Override
    public ItemImages getItemImagesById(Long id) {
        return itemImagesMapper.getItemImagesById(id);
    }

    @Override
    public List<ItemImages> getItemImagesByIid(Long Iid) {
        return itemImagesMapper.getItemImagesByIid(Iid);
    }
}
