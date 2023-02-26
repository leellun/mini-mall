package com.newland.mall.recommend.controller.admin;

import com.github.pagehelper.PageInfo;
import com.newland.mall.model.RestResponse;
import com.newland.mall.recommend.entity.IndexConfig;
import com.newland.mall.recommend.model.dto.BatchIdDTO;
import com.newland.mall.recommend.model.dto.IndexConfigListDTO;
import com.newland.mall.recommend.model.dto.IndexConfigParamDTO;
import com.newland.mall.recommend.service.IndexConfigService;
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

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器
 *
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
@RestController
@RequestMapping("/admin/indexConfig")
@Tag(name = "首页配置", description = "")
public class IndexConfigAdminController {
    @Autowired
    private IndexConfigService indexConfigService;

    /**
     * 列表
     */
    @Parameters({
            @Parameter(name = "pageNo", description = "页码"),
            @Parameter(name = "pageSize", description = "每页条数"),
            @Parameter(name = "configType", description = "1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Operation(method = "首页配置列表", description = "首页配置列表")
    public RestResponse<PageInfo<IndexConfig>> list(@RequestParam(required = false) Integer pageNo,
                                                    @RequestParam(required = false) Integer pageSize,
                                                    @RequestParam(required = false) Integer configType) {
        IndexConfigListDTO dto = new IndexConfigListDTO();
        dto.setConfigType(configType);
        dto.setPageNo(pageNo);
        dto.setPageSize(pageSize);
        return RestResponse.success(indexConfigService.getConfigsPage(dto));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(method = "新增首页配置项", description = "新增首页配置项")
    public RestResponse save(@RequestBody @Validated(Insert.class) IndexConfigParamDTO indexConfigParamDTO) {
        IndexConfig indexConfig = new IndexConfig();
        BeanUtils.copyProperties(indexConfigParamDTO, indexConfig);
        indexConfigService.addIndexConfig(indexConfig);
        return RestResponse.success("添加成功");
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @Operation(method = "修改首页配置项", description = "修改首页配置项")
    public RestResponse update(@RequestBody @Validated(Update.class) IndexConfigParamDTO indexConfigParamDTO) {
        IndexConfig indexConfig = new IndexConfig();
        BeanUtils.copyProperties(indexConfigParamDTO, indexConfig);
        indexConfigService.updateIndexConfig(indexConfig);
        return RestResponse.success("更新成功");
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @Operation(method = "获取单条首页配置项信息", description = "根据id查询")
    public RestResponse<IndexConfig> info(@PathVariable("id") Long id) {
        IndexConfig config = indexConfigService.getIndexConfigById(id);
        return RestResponse.success(config);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.DELETE)
    @Operation(method = "批量删除首页配置项信息", description = "批量删除首页配置项信息")
    public RestResponse delete(@RequestBody BatchIdDTO batchIdParam) {
        indexConfigService.deleteBatch(batchIdParam.getIds());
        return RestResponse.success("删除成功");
    }
}