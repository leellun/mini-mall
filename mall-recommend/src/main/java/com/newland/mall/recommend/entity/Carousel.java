package com.newland.mall.recommend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * sms_carousel
 * @author leell
 * @date 2023-02-25 00:22:56
 */
@Data
public class Carousel implements Serializable {
    /**
     * 首页轮播图主键id
     */
    @Schema(name ="首页轮播图主键id")
    private Long carouselId;

    /**
     * 轮播图
     */
    @Schema(name ="轮播图")
    private String carouselUrl;

    /**
     * 点击后的跳转地址(默认不跳转)
     */
    @Schema(name ="点击后的跳转地址(默认不跳转)")
    private String redirectUrl;

    /**
     * 排序值(字段越大越靠前)
     */
    @Schema(name ="排序值(字段越大越靠前)")
    private Integer carouselRank;

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
     * 创建者id
     */
    @Schema(name ="创建者id")
    private Integer createUser;

    /**
     * 修改时间
     */
    @Schema(name ="修改时间")
    private LocalDateTime updateTime;

    /**
     * 修改者id
     */
    @Schema(name ="修改者id")
    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}