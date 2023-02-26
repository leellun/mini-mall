package com.newland.mall.order.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * 订单列表
 * Author: leell
 * Date: 2023/2/25 14:20:21
 */
@Data
public class OrderListDTO extends PageEntity {
    private Long userId;
    private Integer orderStatus;
}
