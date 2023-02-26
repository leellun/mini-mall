package com.newland.mall.product.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * 分类列表查询
 * Author: leell
 * Date: 2023/2/24 22:02:20
 */
@Data
public class ProductCateListDTO extends PageEntity {
//    private Integer categoryLevel;
    private Long parentId;

}
