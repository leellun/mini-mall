package com.newland.mall.product.controller.app;

import com.newland.mall.model.RestResponse;
import com.newland.mall.product.model.vo.IndexCategoryVO;
import com.newland.mall.product.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  控制器
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
@RestController
@RequestMapping("/productCategory")
@Tag(name = "", description = "")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @GetMapping("/listAll")
    @Operation(method = "获取分类数据", description = "分类页面使用")
    public RestResponse<List<IndexCategoryVO>> getCategories() {
        List<IndexCategoryVO> categories = productCategoryService.getCategoriesForIndex();
        return RestResponse.success(categories);
    }
}