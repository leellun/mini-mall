package com.newland.mall.order.mapper;

import com.newland.mall.order.entity.OrderItem;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Mapper 接口
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Repository
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    /**
     * 根据订单id获取订单项列表
     *
     * @param orderId
     * @return
     */
    List<OrderItem> getByOrderId(@Param("orderId") Long orderId);
}