package com.newland.mall.product.service.impl;

import com.newland.mall.product.entity.Product;
import com.newland.mall.product.mapper.ProductMapper;
import com.newland.mall.product.service.ProductService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}