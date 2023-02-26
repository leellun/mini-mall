package com.newland.mall.product.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * 商品列表后台分页
 * Author: leell
 * Date: 2023/2/24 10:44:52
 */
@Data
public class ProductAdminPageDTO extends PageEntity {
    private String goodsName;
    private Integer goodsSellStatus;
}
