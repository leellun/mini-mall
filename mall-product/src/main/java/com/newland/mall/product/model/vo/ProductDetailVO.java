package com.newland.mall.product.model.vo;

import com.newland.mall.product.entity.Product;
import com.newland.mall.product.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商品详情
 * Author: leell
 * Date: 2023/2/24 12:40:39
 */
@Schema(name = "商品详情")
@Data
public class ProductDetailVO {
    private Product product;
    /**
     * 商品分类
     */
    private ProductCategory productCategory;
}
