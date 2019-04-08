package cn.jianwoo.eshop.cart.service.impl;

import cn.jianwoo.eshop.cart.api.CartService;
import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Item;
import cn.jianwoo.eshop.manage.mapper.ItemMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车处理服务
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ItemMapper itemMapper;
    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    @Override
    public EShopResult addCart(Long userId, Long itemId, int num) {
        Boolean hasItem = redisTemplate.opsForHash().hasKey(REDIS_CART_PRE + ":" + userId, itemId+"");
        if (hasItem) {
            // 商品存在，数量相加
            Item item = (Item) redisTemplate.opsForHash().get(REDIS_CART_PRE + ":" + userId, itemId+"");
            item.setSelnum(item.getSelnum() + num);
            redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId+"", item);
            return EShopResult.ok();
        } else {
            // 商品不存在，查询数据库添加商品
            Item item = itemMapper.getById(itemId);
            item.setSelnum(num);
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                item.setImage(image.split(",")[0]);
            }
            redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId+"", item);
            return EShopResult.ok();
        }
    }

    @Override
    public EShopResult mergeCart(Long userId, List<Item> cookieItemList) {
        for (Item item : cookieItemList) {
            addCart(userId, item.getId(), item.getSelnum());
        }
        return EShopResult.ok();
    }

    @Override
    public List<Item> getCartList(Long userId) {
        List<Object> results = redisTemplate.opsForHash().values(REDIS_CART_PRE + ":" + userId);
        List<Item> items = new ArrayList<>();
        for (Object result : results) {
            items.add((Item) result);
        }
        return items;
    }

    @Override
    public EShopResult updateCartNum(Long userId, Long itemId, int num) {
        Item item = (Item) redisTemplate.opsForHash().get(REDIS_CART_PRE + ":" + userId, itemId+"");
        item.setSelnum(item.getSelnum() + num);
        redisTemplate.opsForHash().put(REDIS_CART_PRE + ":" + userId, itemId+"", item);
        return EShopResult.ok();
    }

    @Override
    public EShopResult deleteCartItem(Long userId, Long itemId) {
        redisTemplate.opsForHash().delete(REDIS_CART_PRE + ":" + userId, itemId+"");
        return EShopResult.ok();
    }

    /**
     * 清除购物车
     * @param userId
     * @return
     */
    @Override
    public EShopResult clearCartList(Long userId) {
        redisTemplate.delete(REDIS_CART_PRE + ":" + userId);
        return EShopResult.ok();
    }


}
