package com.newland.mall.cart.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * 购物车分页
 * Author: leell
 * Date: 2023/2/23 23:57:02
 */
@Data
public class CartItemPageDTO extends PageEntity {
    private Long userId;
}
