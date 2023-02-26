package com.newland.mall.product.service.impl;

import com.newland.mall.product.entity.ProductCategory;
import com.newland.mall.product.mapper.ProductCategoryMapper;
import com.newland.mall.product.service.ProductCategoryService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
}