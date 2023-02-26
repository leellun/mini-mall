package com.newland.mall.recommend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 首页轮播图VO
 */
@Schema(name = "首页轮播图")
@Data
public class IndexCarouselVO implements Serializable {

    @Schema(name = "轮播图图片地址")
    private String carouselUrl;

    @Schema(name = "轮播图点击后的跳转路径")
    private String redirectUrl;
}
