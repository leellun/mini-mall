package com.newland.mall.product.controller;

import com.newland.mall.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  控制器
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
@RestController
@RequestMapping("/product")
@Tag(name = "", description = "")
public class ProductController {
    @Autowired
    private ProductService productService;
}