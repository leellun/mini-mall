package com.newland.mall.order.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单详情页页面订单项VO
 */
@Schema(name = "订单详情页页面订单项")
@Data
public class OrderItemVO implements Serializable {

    @Schema(name = "商品id")
    private Long goodsId;

    @Schema(name = "商品数量")
    private Integer goodsCount;

    @Schema(name = "商品名称")
    private String goodsName;

    @Schema(name = "商品图片")
    private String goodsCoverImg;

    @Schema(name = "商品价格")
    private Integer sellingPrice;
}
