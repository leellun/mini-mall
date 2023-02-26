package com.newland.mall.cart.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改购物项
 */
@Schema(name = "修改购物项")
@Data
public class UpdateCartItemDTO implements Serializable {

    @Schema(name = "购物项id")
    private Long cartItemId;

    @Schema(name = "商品数量")
    private Integer goodsCount;
}
