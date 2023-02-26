package com.newland.mall.product.model.vo;

import com.newland.mall.product.entity.ProductCategory;
import lombok.Data;

import java.util.List;

/**
 * 商品分类列表
 * Author: leell
 * Date: 2023/2/24 22:10:45
 */
@Data
public class ProductCateSelectItemVO extends ProductCategory{
    private List<ProductCateSelectItemVO> children;
}
