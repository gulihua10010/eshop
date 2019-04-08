package cn.jianwoo.eshop.cart.api;


import cn.jianwoo.eshop.common.response.EShopResult;
import cn.jianwoo.eshop.manage.entity.Item;

import java.util.List;

public interface CartService {
    EShopResult addCart(Long userId, Long itemId, int num);
    EShopResult mergeCart(Long userId, List<Item> cookieItemList);
    List<Item> getCartList(Long userId);
    EShopResult updateCartNum(Long userId, Long itemId, int num);
    EShopResult deleteCartItem(Long userId, Long itemId);
    EShopResult clearCartList(Long userId);
}
