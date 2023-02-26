package com.newland.mall.product.controller.admin;

import com.github.pagehelper.PageInfo;
import com.newland.mall.model.RestResponse;
import com.newland.mall.product.entity.ProductCategory;
import com.newland.mall.product.model.dto.BatchIdDTO;
import com.newland.mall.product.model.dto.ProductCateListDTO;
import com.newland.mall.product.model.dto.ProductCategoryParamDTO;
import com.newland.mall.product.model.vo.ProductCateSelectItemVO;
import com.newland.mall.product.model.vo.ProductCateSelectListVO;
import com.newland.mall.product.service.ProductCategoryService;
import com.newland.mall.validator.Insert;
import com.newland.mall.validator.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/productCategory")
@Tag(name = "商品分类", description = "")
public class ProductCategoryAdminController {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 列表
     */
    @Parameters({
            @Parameter(name = "pageNo", description = "页码"),
            @Parameter(name = "pageSize", description = "每页条数"),
            @Parameter(name = "categoryLevel", description = "分类级别"),
            @Parameter(name = "parentId", description = "上级分类的id"),

    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Operation(method = "商品分类列表", description = "根据级别和上级分类id查询")
    public RestResponse<PageInfo<ProductCategory>> list(@RequestParam Integer pageNo,
                                                        @RequestParam Integer pageSize,
                                                        @RequestParam Long parentId) {

        ProductCateListDTO dto = new ProductCateListDTO();
        dto.setPageNo(pageNo);
        dto.setPageSize(pageSize);
        dto.setParentId(parentId);
        return RestResponse.success(productCategoryService.getCategoryPage(dto));
    }
    @GetMapping(value = "/listAllSelect")
    @Operation(method = "商品所有分类列表", description = "用于三级分类联动效果制作")
    public RestResponse<List<ProductCateSelectItemVO>> listAllForSelect() {
        List<ProductCateSelectItemVO> productCateSelectListVO = productCategoryService.getAllCategoryListForSelect();
        return RestResponse.success(productCateSelectListVO);
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list4Select", method = RequestMethod.GET)
    @Operation(method = "商品分类列表", description = "用于三级分类联动效果制作")
    public RestResponse<ProductCateSelectListVO> listForSelect(@RequestParam("categoryId") Long categoryId) {
        ProductCateSelectListVO productCateSelectListVO = productCategoryService.getCategoryListForSelect(categoryId);
        return RestResponse.success(productCateSelectListVO);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(method = "新增分类", description = "新增分类")
    public RestResponse add(@RequestBody @Validated(Insert.class) ProductCategoryParamDTO productCategoryParamDTO) {
        ProductCategory goodsCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryParamDTO, goodsCategory);
        productCategoryService.addCategory(goodsCategory);
        return RestResponse.success("添加成功");
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @Operation(method = "修改分类信息", description = "修改分类信息")
    public RestResponse update(@RequestBody @Validated(Update.class) ProductCategoryParamDTO productCategoryParamDTO) {
        ProductCategory goodsCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryParamDTO, goodsCategory);
        productCategoryService.updateCategory(goodsCategory);
        return RestResponse.success("修改成功");
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @Operation(method = "获取单条分类信息", description = "根据id查询")
    public RestResponse<ProductCategory> info(@PathVariable("id") Long id) {
        ProductCategory productCategory = productCategoryService.getById(id);
        if (productCategory == null) {
            return RestResponse.error("未查询到数据");
        }
        return RestResponse.success(productCategory);
    }

    /**
     * 分类删除
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.DELETE)
    @Operation(method = "批量删除分类信息", description = "批量删除分类信息")
    public RestResponse delete(@RequestBody BatchIdDTO batchIdParam) {
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return RestResponse.error("参数异常！");
        }
        productCategoryService.deleteBatch(batchIdParam.getIds());
        return RestResponse.success("删除成功！");
    }
}