package com.newland.mall.product.service;

import com.github.pagehelper.PageInfo;
import com.newland.mall.product.entity.Product;
import com.newland.mall.product.model.dto.ProductAddDTO;
import com.newland.mall.product.model.dto.ProductAdminPageDTO;
import com.newland.mall.product.model.dto.ProductSearchDTO;
import com.newland.mall.product.model.dto.StockNumDTO;
import com.newland.mall.product.model.vo.MallProductDetailVO;
import com.newland.mall.product.model.vo.ProductDetailVO;
import com.newland.mall.product.model.vo.SearchProductsVO;
import com.newland.mybatis.page.PageEntity;
import com.newland.mybatis.service.IService;

import java.util.List;

/**
 *  服务类
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
public interface ProductService extends IService<Product> {
    /**
     * 后台分页
     *
     * @param productAdminPageDTO
     * @return
     */
    PageInfo<Product> getProductListPage(ProductAdminPageDTO productAdminPageDTO);

    /**
     * 添加商品
     *
     * @param productAddDTO
     * @return
     */
    void addProduct(ProductAddDTO productAddDTO);

    /**
     * 修改商品信息
     *
     * @param productAddDTO
     * @return
     */
    void updateProduct(ProductAddDTO productAddDTO);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    void updateBatchSellStatus(Long[] ids, int sellStatus);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    Product getProductDetail(Long id);

    /**
     * 获取商品数据
     *
     * @param goodsIds
     * @return
     */
    List<Product> getProductsByIds(List<Long> goodsIds);

    /**
     * 商品搜索
     *
     * @param dto
     * @return
     */
    PageInfo<SearchProductsVO> getSearchGoods(ProductSearchDTO dto);

    /**
     * 修改库存
     * @param stockNumDTOS
     */
    void updateStockNum(List<StockNumDTO> stockNumDTOS);

    /**
     * 获取详情页信息
     * @param goodsId
     * @return
     */
    MallProductDetailVO getMallProductDetail(Long goodsId);
}