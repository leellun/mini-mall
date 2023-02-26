package com.newland.mall.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * oms_order_item
 * @author leell
 * @date 2023-02-25 14:07:25
 */
@Data
public class OrderItem implements Serializable {
    /**
     * 订单关联购物项主键id
     */
    @Schema(name ="订单关联购物项主键id")
    private Long orderItemId;

    /**
     * 订单主键id
     */
    @Schema(name ="订单主键id")
    private Long orderId;

    /**
     * 关联商品id
     */
    @Schema(name ="关联商品id")
    private Long goodsId;

    /**
     * 下单时商品的名称(订单快照)
     */
    @Schema(name ="下单时商品的名称(订单快照)")
    private String goodsName;

    /**
     * 下单时商品的主图(订单快照)
     */
    @Schema(name ="下单时商品的主图(订单快照)")
    private String goodsCoverImg;

    /**
     * 下单时商品的价格(订单快照)
     */
    @Schema(name ="下单时商品的价格(订单快照)")
    private Integer sellingPrice;

    /**
     * 数量(订单快照)
     */
    @Schema(name ="数量(订单快照)")
    private Integer goodsCount;

    /**
     * 创建时间
     */
    @Schema(name ="创建时间")
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}