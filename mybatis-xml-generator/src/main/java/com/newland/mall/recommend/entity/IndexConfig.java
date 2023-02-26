package com.newland.mall.recommend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * sms_index_config
 * @author leell
 * @date 2023-02-25 00:22:56
 */
@Data
public class IndexConfig implements Serializable {
    /**
     * 首页配置项主键id
     */
    @Schema(name ="首页配置项主键id")
    private Long configId;

    /**
     * 显示字符(配置搜索时不可为空，其他可为空)
     */
    @Schema(name ="显示字符(配置搜索时不可为空，其他可为空)")
    private String configName;

    /**
     * 1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
     */
    @Schema(name ="1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐")
    private Integer configType;

    /**
     * 商品id 默认为0
     */
    @Schema(name ="商品id 默认为0")
    private Long goodsId;

    /**
     * 点击后的跳转地址(默认不跳转)
     */
    @Schema(name ="点击后的跳转地址(默认不跳转)")
    private String redirectUrl;

    /**
     * 排序值(字段越大越靠前)
     */
    @Schema(name ="排序值(字段越大越靠前)")
    private Integer configRank;

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
     * 最新修改时间
     */
    @Schema(name ="最新修改时间")
    private LocalDateTime updateTime;

    /**
     * 修改者id
     */
    @Schema(name ="修改者id")
    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}