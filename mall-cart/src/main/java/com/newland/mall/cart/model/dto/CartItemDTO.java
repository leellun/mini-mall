package com.newland.mall.cart.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 添加购物项
 */
@Schema(name = "添加购物项")
@Data
public class CartItemDTO implements Serializable {

    @Schema(name = "商品数量")
    private Integer goodsCount;

    @Schema(name = "商品id")
    private Long goodsId;
}
