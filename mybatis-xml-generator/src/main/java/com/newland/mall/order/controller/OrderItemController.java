package com.newland.mall.order.controller;

import com.newland.mall.order.service.OrderItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  控制器
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@RestController
@RequestMapping("/orderItem")
@Tag(name = "", description = "")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;
}