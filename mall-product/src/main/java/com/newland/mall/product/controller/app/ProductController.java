package com.newland.mall.product.controller.app;

import com.github.pagehelper.PageInfo;
import com.newland.mall.model.RestResponse;
import com.newland.mall.product.model.dto.ProductSearchDTO;
import com.newland.mall.product.model.vo.MallProductDetailVO;
import com.newland.mall.product.model.vo.SearchProductsVO;
import com.newland.mall.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器
 *
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
@RestController
@RequestMapping("/product")
@Tag(name = "", description = "")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(method = "商品搜索接口", description = "根据关键字和分类id进行搜索")
    @Parameters({
            @Parameter(name = "keyword", description = "搜索关键字", required = false),
            @Parameter(name = "goodsCategoryId", description = "分类id", required = false),
            @Parameter(name = "orderBy", description = "排序", required = false),
            @Parameter(name = "pageNumber", description = "页码", required = false)
    })
    @GetMapping("/search")
    public RestResponse<PageInfo<SearchProductsVO>> search(@RequestParam(required = false) String keyword,
                                                                 @RequestParam(required = false) Long goodsCategoryId,
                                                                 @RequestParam(required = false) String orderBy,
                                                                 @RequestParam(required = false) Integer pageNumber) {
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        ProductSearchDTO productSearchDTO=new ProductSearchDTO();
        productSearchDTO.setPageNo(pageNumber);
        productSearchDTO.setPageSize(10);
        productSearchDTO.setGoodsCategoryId(goodsCategoryId);
        productSearchDTO.setOrderBy(orderBy);
        productSearchDTO.setGoodsSellStatus(0);
        return RestResponse.success(productService.getSearchGoods(productSearchDTO));
    }

    @Parameters({
            @Parameter(name = "goodsId", description = "商品id", required = true)
    })
    @GetMapping("/detail/{goodsId}")
    @Operation(method = "商品详情接口", description = "传参为商品id")
    public RestResponse<MallProductDetailVO> goodsDetail(@PathVariable("goodsId") Long goodsId) {
        return RestResponse.success(productService.getMallProductDetail(goodsId));
    }
}