package com.newland.mall.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * 订单收货地址关联表
 * oms_order_address
 * @author leell
 * @date 2023-02-25 14:07:25
 */
@Data
@Schema(name ="订单收货地址关联表")
public class OrderAddress implements Serializable {
    /**
     */
    private Long orderId;

    /**
     * 收货人姓名
     */
    @Schema(name ="收货人姓名")
    private String userName;

    /**
     * 收货人手机号
     */
    @Schema(name ="收货人手机号")
    private String userPhone;

    /**
     * 省
     */
    @Schema(name ="省")
    private String provinceName;

    /**
     * 城
     */
    @Schema(name ="城")
    private String cityName;

    /**
     * 区
     */
    @Schema(name ="区")
    private String regionName;

    /**
     * 收件详细地址(街道/楼宇/单元)
     */
    @Schema(name ="收件详细地址(街道/楼宇/单元)")
    private String detailAddress;

    private static final long serialVersionUID = 1L;
}