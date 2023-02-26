package com.newland.mall.product.controller.admin;

import com.github.pagehelper.PageInfo;
import com.newland.mall.model.RestResponse;
import com.newland.mall.product.entity.Product;
import com.newland.mall.product.model.dto.BatchIdDTO;
import com.newland.mall.product.model.dto.ProductAddDTO;
import com.newland.mall.product.model.dto.ProductAdminPageDTO;
import com.newland.mall.product.model.dto.UpdateStockNumDTO;
import com.newland.mall.product.model.vo.ProductDetailVO;
import com.newland.mall.product.service.ProductService;
import com.newland.mall.validator.Insert;
import com.newland.mall.validator.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制器
 *
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
@RestController
@RequestMapping("/admin/product")
@Tag(name = "商品后台", description = "")
public class ProductAdminController {
    @Autowired
    private ProductService productService;

    /**
     * 列表
     */
    @Parameters({
            @Parameter(name = "pageNo", description = "页码", required = false),
            @Parameter(name = "pageSize", description = "每页条数", required = false),
            @Parameter(name = "goodsName", description = "商品名称", required = false),
            @Parameter(name = "goodsSellStatus", description = "上架状态 0-上架 1-下架", required = false),

    })
    @GetMapping(value = "/list")
    @Operation(method = "商品列表", description = "可根据名称和上架状态筛选")
    public RestResponse<PageInfo<Product>> list(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize,
                                                @RequestParam(required = false) String goodsName, @RequestParam(required = false) Integer goodsSellStatus) {
        if (pageNo == null || pageNo < 1 || pageSize == null || pageSize < 10) {
            return RestResponse.error("分页参数异常！");
        }
        ProductAdminPageDTO productAdminPageDTO = new ProductAdminPageDTO();
        productAdminPageDTO.setGoodsName(goodsName);
        productAdminPageDTO.setGoodsSellStatus(goodsSellStatus);
        productAdminPageDTO.setPageNo(pageNo);
        productAdminPageDTO.setPageSize(pageSize);
        return RestResponse.success(productService.getProductListPage(productAdminPageDTO));
    }

    /**
     * 添加
     */
    @PostMapping(value = "/add")
    @Operation(method = "新增商品信息", description = "新增商品信息")
    public RestResponse add(@RequestBody @Validated(value = Insert.class) ProductAddDTO goodsAddDTO) {
        productService.addProduct(goodsAddDTO);
        return RestResponse.success("新增成功");
    }


    /**
     * 修改
     */
    @PutMapping(value = "/update")
    @Operation(method = "修改商品信息", description = "修改商品信息")
    public RestResponse update(@RequestBody @Validated(Update.class) ProductAddDTO goodsAddDTO) {
        productService.updateProduct(goodsAddDTO);
        return RestResponse.success("操作成功");
    }

    /**
     * 详情
     */
    @GetMapping("/detail/{id}")
    @Operation(method = "获取单条商品信息", description = "根据id查询")
    public RestResponse<Product> info(@PathVariable("id") Long id) {
        Product product = productService.getProductDetail(id);
        return RestResponse.success(product);
    }

    /**
     * 批量修改销售状态
     */
    @PutMapping(value = "/updateStatus/{sellStatus}")
    @Operation(method = "批量修改销售状态", description = "批量修改销售状态")
    public RestResponse delete(@RequestBody BatchIdDTO batchIdParam, @PathVariable("sellStatus") int sellStatus) {
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return RestResponse.error("参数异常！");
        }
        if (sellStatus != 0 && sellStatus != 1) {
            return RestResponse.error("状态异常！");
        }
        productService.updateBatchSellStatus(batchIdParam.getIds(), sellStatus);
        return RestResponse.success("操作成功！");
    }

    /**
     * 详情
     */
    @GetMapping("/goodsDetail")
    @Operation(method = "获取单条商品信息", description = "根据id查询")
    public RestResponse<Product> goodsDetail(@RequestParam("goodsId") Long goodsId) {
        Product goods = productService.getById(goodsId);
        return RestResponse.success(goods);
    }

    /**
     * 根据ids查询商品列表
     */
    @GetMapping("/listByGoodsIds")
    @Operation(method = "根据ids查询商品列表", description = "根据ids查询")
    public RestResponse<List<Product>> getGoodsByIds(@RequestParam("goodsIds") List<Long> goodsIds) {
        List<Product> products = productService.getProductsByIds(goodsIds);
        return RestResponse.success(products);
    }

    /**
     * 修改库存
     */
    @PutMapping("/updateStock")
    @Operation(method = "修改库存", description = "")
    public RestResponse updateStock(@RequestBody UpdateStockNumDTO updateStockNumDTO) {
        productService.updateStockNum(updateStockNumDTO.getStockNumDTOS());
        return RestResponse.success("操作成功");
    }
}