package com.newland.mall.product.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品详情页VO
 */
@Schema(name = "商品详情页VO")
@Data
public class MallProductDetailVO implements Serializable {

    @Schema(name ="商品id")
    private Long goodsId;

    @Schema(name ="商品名称")
    private String goodsName;

    @Schema(name ="商品简介")
    private String goodsIntro;

    @Schema(name ="商品图片地址")
    private String goodsCoverImg;

    @Schema(name ="商品价格")
    private Integer sellingPrice;

    @Schema(name ="商品标签")
    private String tag;

    @Schema(name ="商品图片")
    private String[] goodsCarouselList;

    @Schema(name ="商品原价")
    private Integer originalPrice;

    @Schema(name ="商品详情字段")
    private String goodsDetailContent;
}
