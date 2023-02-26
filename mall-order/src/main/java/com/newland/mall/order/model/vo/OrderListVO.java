package com.newland.mall.order.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单列表页面VO
 */
@Schema(name = "订单列表页面VO")
@Data
public class OrderListVO implements Serializable {

    private Long orderId;

    @Schema(name = "订单号")
    private String orderNo;

    @Schema(name = "订单价格")
    private Integer totalPrice;

    @Schema(name = "订单支付方式")
    private Integer payType;

    @Schema(name = "订单状态码")
    private Integer orderStatus;

    @Schema(name = "订单状态")
    private String orderStatusString;

    @Schema(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Schema(name = "订单项列表")
    private List<OrderItemVO> orderItemVOS;
}
