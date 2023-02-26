package com.newland.mall.product.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 搜索列表页商品VO
 */
@Schema(name = "搜索列表页商品")
@Data
public class SearchProductsVO implements Serializable {

    @Schema(name = "商品id")
    private Long goodsId;

    @Schema(name = "商品名称")
    private String goodsName;

    @Schema(name = "商品简介")
    private String goodsIntro;

    @Schema(name = "商品图片地址")
    private String goodsCoverImg;

    @Schema(name = "商品价格")
    private Integer sellingPrice;

}
