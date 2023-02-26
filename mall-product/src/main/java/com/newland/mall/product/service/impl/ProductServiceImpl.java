package com.newland.mall.product.service.impl;

import com.github.pagehelper.PageInfo;
import com.newland.mall.exception.BusinessException;
import com.newland.mall.model.LoginUser;
import com.newland.mall.product.common.ProductCategoryLevelEnum;
import com.newland.mall.product.entity.Product;
import com.newland.mall.product.entity.ProductCategory;
import com.newland.mall.product.mapper.ProductCategoryMapper;
import com.newland.mall.product.mapper.ProductMapper;
import com.newland.mall.product.model.dto.ProductAddDTO;
import com.newland.mall.product.model.dto.ProductAdminPageDTO;
import com.newland.mall.product.model.dto.ProductSearchDTO;
import com.newland.mall.product.model.dto.StockNumDTO;
import com.newland.mall.product.model.vo.MallProductDetailVO;
import com.newland.mall.product.model.vo.ProductDetailVO;
import com.newland.mall.product.model.vo.SearchProductsVO;
import com.newland.mall.product.service.ProductService;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import com.newland.security.utils.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 *
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public PageInfo<Product> getProductListPage(ProductAdminPageDTO productAdminPageDTO) {
        PageInfo<Product> pageInfo = PageWrapper.page(productAdminPageDTO, () -> baseMapper.listProducts(productAdminPageDTO));
        return pageInfo;
    }

    @Override
    public void addProduct(ProductAddDTO productAddDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productAddDTO, product);
        LoginUser loginUser = SecurityUtil.getLoginUser();
        product.setCreateTime(LocalDateTime.now());
        product.setCreateUser(loginUser.getUserId());
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(product.getGoodsCategoryId());
        AssertUtil.notNull(productCategory,"分类异常");
        if (baseMapper.getByCategoryIdAndName(product.getGoodsName(), product.getGoodsCategoryId()) != null) {
            throw new BusinessException("存在相同的商品");
        }
        baseMapper.insertSelective(product);
    }

    @Override
    public void updateProduct(ProductAddDTO productAddDTO) {
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(productAddDTO.getGoodsCategoryId());
        AssertUtil.notNull(productCategory,"分类异常");
        Product temp = baseMapper.selectByPrimaryKey(productAddDTO.getGoodsId());
        if (temp == null) {
            throw new BusinessException("不存在商品");
        }
        Product temp2 = baseMapper.getByCategoryIdAndName(productAddDTO.getGoodsName(), productAddDTO.getGoodsCategoryId());
        if (temp2 != null && !temp2.getGoodsId().equals(productAddDTO.getGoodsId())) {
            //name和分类id相同且不同id 不能继续修改
            throw new BusinessException("存在相同的商品");
        }
        LoginUser loginUser = SecurityUtil.getLoginUser();
        Product product = new Product();
        BeanUtils.copyProperties(productAddDTO, product);
        product.setUpdateTime(LocalDateTime.now());
        product.setUpdateUser(loginUser.getUserId());
        baseMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public void updateBatchSellStatus(Long[] ids, int sellStatus) {
        baseMapper.updateSellStatus(ids, sellStatus);
    }

    @Override
    public Product getProductDetail(Long id) {
        Product product = baseMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(product, "商品不存在");
        return product;
    }

    @Override
    public List<Product> getProductsByIds(List<Long> goodsIds) {
        return baseMapper.getByIds(goodsIds);
    }

    @Override
    public PageInfo<SearchProductsVO> getSearchGoods(ProductSearchDTO dto) {
        PageInfo<Product> pageInfo = PageWrapper.page(dto, () -> baseMapper.listProductBySearch(dto));
        List<SearchProductsVO> searchGoodsVOS = new ArrayList<>();
        if (pageInfo.getList().size() > 0) {
            pageInfo.getList().forEach(item -> {
                SearchProductsVO searchProductsVO = new SearchProductsVO();
                BeanUtils.copyProperties(item, searchProductsVO);
                String goodsName = searchProductsVO.getGoodsName();
                String goodsIntro = searchProductsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    searchProductsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    searchProductsVO.setGoodsIntro(goodsIntro);
                }

                searchGoodsVOS.add(searchProductsVO);
            });
        }
        return PageWrapper.newPageInfo(pageInfo, searchGoodsVOS);
    }

    @Override
    public void updateStockNum(List<StockNumDTO> stockNumDTOS) {
        baseMapper.updateStockNum(stockNumDTOS);
    }

    @Override
    public MallProductDetailVO getMallProductDetail(Long goodsId) {
        if (goodsId < 1) {
            throw new BusinessException("参数异常");
        }
        Product product=baseMapper.selectByPrimaryKey(goodsId);
        MallProductDetailVO mallProductDetailVO = new MallProductDetailVO();
        BeanUtils.copyProperties(product,mallProductDetailVO);
        if (0 != product.getGoodsSellStatus()) {
            throw new BusinessException("商品已下架！");
        }
        mallProductDetailVO.setGoodsCarouselList(product.getGoodsCarousel().split(","));
        return mallProductDetailVO;
    }
}