package com.newland.mall.order.model.dto;

import lombok.Data;

/**
 * 订单id列表
 * Author: leell
 * Date: 2023/2/26 17:00:15
 */
@Data
public class BatchIdParamDTO {
    private Long[] ids;
}
