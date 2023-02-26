package com.newland.mall.order.service.impl;

import com.newland.mall.order.entity.Order;
import com.newland.mall.order.mapper.OrderMapper;
import com.newland.mall.order.service.OrderService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}