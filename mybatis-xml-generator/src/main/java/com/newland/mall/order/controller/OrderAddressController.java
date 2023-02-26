package com.newland.mall.order.controller;

import com.newland.mall.order.service.OrderAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单收货地址关联表 控制器
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@RestController
@RequestMapping("/orderAddress")
@Tag(name = "订单收货地址关联表", description = "订单收货地址关联表")
public class OrderAddressController {
    @Autowired
    private OrderAddressService orderAddressService;
}