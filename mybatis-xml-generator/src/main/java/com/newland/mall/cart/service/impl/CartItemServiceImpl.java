package com.newland.mall.cart.service.impl;

import com.newland.mall.cart.entity.CartItem;
import com.newland.mall.cart.mapper.CartItemMapper;
import com.newland.mall.cart.service.CartItemService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author leellun
 * @since 2023-02-23 23:30:31
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {
}