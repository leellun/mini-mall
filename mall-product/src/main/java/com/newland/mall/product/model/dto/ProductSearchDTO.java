package com.newland.mall.product.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * 商品搜索
 * Author: leell
 * Date: 2023/2/24 20:57:58
 */
@Data
public class ProductSearchDTO extends PageEntity {
    private String keyword;
    private Long goodsCategoryId;
    private String orderBy;
    /**
     * 搜索上架状态下的商品
     */
    private Integer goodsSellStatus;
}
