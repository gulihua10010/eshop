package cn.jianwooeshop.order.service.impl;

import cn.jianwoo.eshop.api.OrderItemService;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.OrderItem;
import cn.jianwoo.eshop.manage.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Transactional
    @Override
    public EShopResult insert(OrderItem record) {
        if (record!=null){
            orderItemMapper.insert(record);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }
    @Transactional
    @Override
    public EShopResult update(OrderItem record) {
        if (record!=null&&record.getId()!=null){
            orderItemMapper.update(record);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }
    @Transactional
    @Override
    public EShopResult delete(OrderItem record) {
        if (record!=null&&record.getId()!=null){
            orderItemMapper.delete(record);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Transactional
    @Override
    public EShopResult deletebyoid(OrderItem record) {
        if (record!=null&&record.getOrderId()!=null){
            orderItemMapper.deletebyoid(record);
            return  EShopResult.ok();
        }
        return EShopResult.error("不能为空");
    }

    @Override
    public List<OrderItem> getOrderItemListByMap(Map<String, Object> params) {
        return orderItemMapper.getOrderItemListByMap(params);
    }

    @Override
    public OrderItem getOrderItem(Map<String, Object> params) {
        return orderItemMapper.getOrderItem(params);
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemMapper.getOrderItemById(id);
    }

    @Override
    public List<OrderItem> getOrderItemByoId(String orderId) {
        return orderItemMapper.getOrderItemByoId(orderId);
    }

    @Override
    public List<OrderItem> getOrderItemList() {
        return orderItemMapper.getOrderItemList();
    }
}
