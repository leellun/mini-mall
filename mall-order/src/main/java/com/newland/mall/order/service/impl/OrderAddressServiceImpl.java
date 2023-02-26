package com.newland.mall.order.service.impl;

import com.newland.mall.order.entity.OrderAddress;
import com.newland.mall.order.mapper.OrderAddressMapper;
import com.newland.mall.order.service.OrderAddressService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 订单收货地址关联表 服务实现类
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Service
public class OrderAddressServiceImpl extends ServiceImpl<OrderAddressMapper, OrderAddress> implements OrderAddressService {
}