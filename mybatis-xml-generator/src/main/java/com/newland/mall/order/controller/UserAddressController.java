package com.newland.mall.order.controller;

import com.newland.mall.order.service.UserAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收货地址表 控制器
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@RestController
@RequestMapping("/userAddress")
@Tag(name = "收货地址表", description = "收货地址表")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;
}