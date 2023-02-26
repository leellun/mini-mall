package com.newland.mall.product.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * pms_product_category
 * @author leell
 * @date 2023-02-24 10:30:33
 */
@Data
public class ProductCategory implements Serializable {
    /**
     * 分类id
     */
    @Schema(name ="分类id")
    private Long categoryId;

    /**
     * 分类级别(1-一级分类 2-二级分类 3-三级分类)
     */
    @Schema(name ="分类级别(1-一级分类 2-二级分类 3-三级分类)")
    private Integer categoryLevel;

    /**
     * 父分类id
     */
    @Schema(name ="父分类id")
    private Long parentId;

    /**
     * 分类名称
     */
    @Schema(name ="分类名称")
    private String categoryName;

    /**
     * 排序值(字段越大越靠前)
     */
    @Schema(name ="排序值(字段越大越靠前)")
    private Integer categoryRank;

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