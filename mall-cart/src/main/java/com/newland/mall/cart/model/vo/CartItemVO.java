package com.newland.mall.cart.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 购物车页面购物项VO
 */
@Schema(name = "购物车页面购物项")
@Data
public class CartItemVO implements Serializable {

    @Schema(name ="购物项id")
    private Long cartItemId;

    @Schema(name ="商品id")
    private Long goodsId;

    @Schema(name ="商品数量")
    private Integer goodsCount;

    @Schema(name ="商品名称")
    private String goodsName;

    @Schema(name ="商品图片")
    private String goodsCoverImg;

    @Schema(name ="商品价格")
    private Integer sellingPrice;
}
