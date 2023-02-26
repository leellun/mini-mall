package com.newland.mall.order.service.impl;

import com.newland.mall.order.entity.OrderItem;
import com.newland.mall.order.mapper.OrderItemMapper;
import com.newland.mall.order.service.OrderItemService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {
}