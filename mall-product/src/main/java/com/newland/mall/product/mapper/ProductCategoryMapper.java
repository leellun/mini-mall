package com.newland.mall.product.mapper;

import com.newland.mall.product.entity.ProductCategory;
import com.newland.mall.product.model.dto.ProductCateListDTO;
import com.newland.mall.product.model.vo.ProductCateSelectItemVO;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Mapper 接口
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
@Repository
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    /**
     * 根据等级和分类名获取分类
     * @param parentId
     * @param categoryName
     * @return
     */
    ProductCategory getByParentIdAndName(@Param("parentId") Long parentId, @Param("categoryName") String categoryName);

    /**
     * 获取商品列表
     * @param dto
     * @return
     */
    List<ProductCategory> getCategoryList(ProductCateListDTO dto);

    /**
     * 通过级别 父级 获取分类子列表
     * @param parentIds
     * @param categoryLevel
     * @param number
     * @return
     */
    List<ProductCategory> getByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Long[] ids);

    /**
     * 通过排序查找所有
     * @return
     */
    List<ProductCateSelectItemVO> selectAllByRank();
}