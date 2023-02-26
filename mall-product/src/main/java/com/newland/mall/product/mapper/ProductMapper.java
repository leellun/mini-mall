package com.newland.mall.product.mapper;

import com.newland.mall.product.entity.Product;
import com.newland.mall.product.model.dto.ProductAdminPageDTO;
import com.newland.mall.product.model.dto.ProductSearchDTO;
import com.newland.mall.product.model.dto.StockNumDTO;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Mapper 接口
 * @author leellun
 * @since 2023-02-24 10:30:32
 */
@Repository
public interface ProductMapper extends BaseMapper<Product> {
    /**
     * 商品列表
     * @param dto
     * @return
     */
    List<Product> listProducts(ProductAdminPageDTO dto);

    /**
     * 通过名称和分类查询商品
     * @param goodsName
     * @param goodsCategoryId
     * @return
     */
    Product getByCategoryIdAndName(@Param("goodsName") String goodsName, @Param("goodsCategoryId") Long goodsCategoryId);

    /**
     * 更新状态
     * @param orderIds id列表
     * @param sellStatus
     * @return
     */
    Integer updateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

    /**
     * 更新库存
     * @param stockNumDTOS
     * @return
     */
    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    /**
     * 通过id列表获取商品
     * @param ids
     * @return
     */
    List<Product> getByIds(@Param("ids") List<Long> ids);

    /**
     * 搜索
     * @param dto
     * @return
     */
    List<Product> listProductBySearch(ProductSearchDTO dto);
}