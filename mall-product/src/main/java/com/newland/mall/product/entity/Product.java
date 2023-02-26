package com.newland.mall.product.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * pms_product
 * @author leell
 * @date 2023-02-24 10:30:32
 */
@Data
public class Product implements Serializable {
    /**
     * 商品表主键id
     */
    @Schema(name ="商品表主键id")
    private Long goodsId;

    /**
     * 商品名
     */
    @Schema(name ="商品名")
    private String goodsName;

    /**
     * 商品简介
     */
    @Schema(name ="商品简介")
    private String goodsIntro;

    /**
     * 关联分类id
     */
    @Schema(name ="关联分类id")
    private Long goodsCategoryId;

    /**
     * 商品主图
     */
    @Schema(name ="商品主图")
    private String goodsCoverImg;

    /**
     * 商品轮播图
     */
    @Schema(name ="商品轮播图")
    private String goodsCarousel;

    /**
     * 商品价格
     */
    @Schema(name ="商品价格")
    private Integer originalPrice;

    /**
     * 商品实际售价
     */
    @Schema(name ="商品实际售价")
    private Integer sellingPrice;

    /**
     * 商品库存数量
     */
    @Schema(name ="商品库存数量")
    private Integer stockNum;

    /**
     * 商品标签
     */
    @Schema(name ="商品标签")
    private String tag;

    /**
     * 商品上架状态 1-下架 0-上架
     */
    @Schema(name ="商品上架状态 1-下架 0-上架")
    private Integer goodsSellStatus;

    /**
     * 添加者主键id
     */
    @Schema(name ="添加者主键id")
    private Long createUser;

    /**
     * 商品添加时间
     */
    @Schema(name ="商品添加时间")
    private LocalDateTime createTime;

    /**
     * 修改者主键id
     */
    @Schema(name ="修改者主键id")
    private Long updateUser;

    /**
     * 商品修改时间
     */
    @Schema(name ="商品修改时间")
    private LocalDateTime updateTime;

    /**
     * 商品详情
     */
    @Schema(name ="商品详情")
    private String goodsDetailContent;

    private static final long serialVersionUID = 1L;
}