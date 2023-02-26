package com.newland.mall.cart.service;

import com.github.pagehelper.PageInfo;
import com.newland.mall.cart.entity.CartItem;
import com.newland.mall.cart.model.dto.CartItemDTO;
import com.newland.mall.cart.model.dto.CartItemPageDTO;
import com.newland.mall.cart.model.dto.UpdateCartItemDTO;
import com.newland.mall.cart.model.vo.CartItemVO;
import com.newland.mybatis.service.IService;

import java.util.List;

/**
 *  服务类
 * @author leellun
 * @since 2023-02-23 23:30:31
 */
public interface CartItemService extends IService<CartItem> {
    /**
     * 保存商品至购物车中
     *
     * @param saveCartItemParam
     * @param userId
     * @return
     */
    void addCartItem(CartItemDTO saveCartItemParam, Long userId);

    /**
     * 修改购物车中的属性
     *
     * @param updateCartItemParam
     * @param userId
     * @return
     */
    void updateCartItem(UpdateCartItemDTO updateCartItemParam, Long userId);

    /**
     * 获取购物项详情
     *
     * @param CartItemId
     * @return
     */
//    CartItem getCartItemById(Long CartItemId);

    /**
     * 删除购物车中的商品
     *
     * @param shoppingCartItemId
     * @param userId
     * @return
     */
    void deleteCartItem(Long shoppingCartItemId, Long userId);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param userId
     * @return
     */
    List<CartItemVO> getMyShoppingCartItems(Long userId);

    /**
     * 根据userId和cartItemIds获取对应的购物项记录
     *
     * @param cartItemIds
     * @param userId
     * @return
     */
    List<CartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long userId);


    /**
     * 根据cartItemIds获取对应的购物项记录
     *
     * @param cartItemIds
     * @return
     */
    List<CartItem> getCartItemsByCartIds(List<Long> cartItemIds);

    /**
     * 批量删除购物项记录
     *
     * @param cartItemIds
     * @return
     */
    void deleteCartItemsByCartIds(List<Long> cartItemIds);

    /**
     * 我的购物车(分页数据)
     *
     * @param cartItemPageDTO
     * @return
     */
    PageInfo<CartItemVO> getCartItemPageList(CartItemPageDTO cartItemPageDTO);
}