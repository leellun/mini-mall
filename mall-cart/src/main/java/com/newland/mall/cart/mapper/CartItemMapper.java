package com.newland.mall.cart.mapper;

import com.newland.mall.cart.entity.CartItem;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Mapper 接口
 * @author leellun
 * @since 2023-02-23 23:30:31
 */
@Repository
public interface CartItemMapper extends BaseMapper<CartItem> {
    /**
     * 查询用户购物车
     * @param userId
     * @return
     */
    List<CartItem> listCartItems(Long userId);

    /**
     * 批量删除
     * @param ids
     */
    int deleteBatch(List<Long> ids);

    /**
     * 通过用户id和商品id查找购物车商品
     * @param userId
     * @param goodsId
     * @return
     */
    CartItem getByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    /**
     * 通过用户id和购物itemid列表获取
     * @param userId
     * @param cartItemIds
     * @return
     */
    List<CartItem> listByUserIdAndCartIds(@Param("userId") Long userId, @Param("cartItemIds") List<Long> cartItemIds);

    /**
     * 通cartitem id列表获取
     * @param cartItemIds
     * @return
     */
    List<CartItem> listByCartItemIds(@Param("cartItemIds") List<Long> cartItemIds);

    /**
     * 统计用户购物车数量
     * @param userId
     * @return
     */
    Long countByUserId(@Param("userId") Long userId);
}