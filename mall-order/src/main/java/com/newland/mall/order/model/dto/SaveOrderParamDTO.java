package com.newland.mall.order.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 保存订单
 */
@Schema(name = "保存订单")
@Data
public class SaveOrderParamDTO implements Serializable {

    @Schema(name ="订单项id数组")
    @NotNull(message = "商品不能为空")
    private Long[] cartItemIds;

    @Schema(name ="地址id")
    @NotNull(message = "收获地址未选择")
    private Long addressId;
}
