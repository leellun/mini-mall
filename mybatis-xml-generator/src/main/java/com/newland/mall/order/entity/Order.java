package com.newland.mall.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * oms_order
 * @author leell
 * @date 2023-02-25 14:07:25
 */
@Data
public class Order implements Serializable {
    /**
     * 订单表主键id
     */
    @Schema(name ="订单表主键id")
    private Long orderId;

    /**
     * 订单号
     */
    @Schema(name ="订单号")
    private String orderNo;

    /**
     * 用户主键id
     */
    @Schema(name ="用户主键id")
    private Long userId;

    /**
     * 订单总价
     */
    @Schema(name ="订单总价")
    private Integer totalPrice;

    /**
     * 支付状态:0.未支付,1.支付成功,-1:支付失败
     */
    @Schema(name ="支付状态:0.未支付,1.支付成功,-1:支付失败")
    private Integer payStatus;

    /**
     * 0.无 1.支付宝支付 2.微信支付
     */
    @Schema(name ="0.无 1.支付宝支付 2.微信支付")
    private Integer payType;

    /**
     * 支付时间
     */
    @Schema(name ="支付时间")
    private LocalDateTime payTime;

    /**
     * 订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭
     */
    @Schema(name ="订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭")
    private Integer orderStatus;

    /**
     * 订单body
     */
    @Schema(name ="订单body")
    private String extraInfo;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    @Schema(name ="删除标识字段(0-未删除 1-已删除)")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @Schema(name ="创建时间")
    private LocalDateTime createTime;

    /**
     * 最新修改时间
     */
    @Schema(name ="最新修改时间")
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}