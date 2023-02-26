package com.newland.mall.cart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * cart_item
 * @author leell
 * @date 2023-02-23 23:30:31
 */
@Data
public class CartItemVO implements Serializable {
    /**
     * 购物项主键id
     */
    @Schema(name ="购物项主键id")
    private Long cartItemId;

    /**
     * 用户主键id
     */
    @Schema(name ="用户主键id")
    private Long userId;

    /**
     * 关联商品id
     */
    @Schema(name ="关联商品id")
    private Long goodsId;

    /**
     * 数量(最大为5)
     */
    @Schema(name ="数量(最大为5)")
    private Integer goodsCount;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    @Schema(name ="删除标识字段(0-未删除 1-已删除)")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @Schema(name ="创建时间")
    private LocalDateTime createTime;

    /**
     * 最新修改时间
     */
    @Schema(name ="最新修改时间")
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}