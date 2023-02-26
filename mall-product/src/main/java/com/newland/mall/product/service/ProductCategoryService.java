package com.newland.mall.product.service;

import com.github.pagehelper.PageInfo;
import com.newland.mall.product.entity.ProductCategory;
import com.newland.mall.product.model.dto.ProductCateListDTO;
import com.newland.mall.product.model.vo.IndexCategoryVO;
import com.newland.mall.product.model.vo.ProductCateSelectItemVO;
import com.newland.mall.product.model.vo.ProductCateSelectListVO;
import com.newland.mybatis.service.IService;

import java.util.List;

/**
 *  服务类
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    void addCategory(ProductCategory productCategory);

    void updateCategory(ProductCategory productCategory);

    void deleteBatch(Long[] ids);

    /**
     * 后台分页
     *
     * @param dto
     * @return
     */
    PageInfo<ProductCategory> getCategoryPage(ProductCateListDTO dto);

    /**
     * 返回分类数据(首页调用)
     *
     * @return
     */
    List<IndexCategoryVO> getCategoriesForIndex();

    /**
     * 获取分类列表 用于三级分类联动效果制作
     * @param categoryId
     * @return
     */
    ProductCateSelectListVO getCategoryListForSelect(Long categoryId);

    /**
     * 获取所有分类列表 用于三级分类联动效果制作
     * @return
     */
    List<ProductCateSelectItemVO> getAllCategoryListForSelect();
}