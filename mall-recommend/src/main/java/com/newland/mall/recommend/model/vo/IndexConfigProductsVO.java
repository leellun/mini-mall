package com.newland.mall.recommend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 首页配置商品VO
 */
@Schema(name = "首页配置商品")
@Data
public class IndexConfigProductsVO implements Serializable {

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
}
