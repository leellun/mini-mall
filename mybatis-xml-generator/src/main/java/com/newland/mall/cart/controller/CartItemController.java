package com.newland.mall.cart.controller;

import com.newland.mall.cart.service.CartItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  控制器
 * @author leellun
 * @since 2023-02-23 23:30:31
 */
@RestController
@RequestMapping("/cartItem")
@Tag(name = "", description = "")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
}