package com.newland.mall.order.mapper;

import com.newland.mall.order.entity.OrderAddress;
import com.newland.mybatis.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 订单收货地址关联表 Mapper 接口
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Repository
public interface OrderAddressMapper extends BaseMapper<OrderAddress> {
}