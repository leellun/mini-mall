package com.newland.mall.product.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页分类数据VO
 */
@Schema(name = "首页分类数据")
@Data
public class LevelCategoryVO implements Serializable {

    @Schema(name = "当前二级分类id")
    private Long categoryId;

    @Schema(name = "父级分类id")
    private Long parentId;

    @Schema(name = "当前分类级别")
    private Integer categoryLevel;

    @Schema(name = "当前二级分类名称")
    private String categoryName;

    @Schema(name = "三级分类列表")
    private List<LevelCategoryVO> thirdLevelCategoryVOS;
}
