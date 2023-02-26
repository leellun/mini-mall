package com.newland.mall.recommend.controller.admin;

import com.github.pagehelper.PageInfo;
import com.newland.mall.model.RestResponse;
import com.newland.mall.recommend.entity.Carousel;
import com.newland.mall.recommend.model.dto.BatchIdDTO;
import com.newland.mall.recommend.model.dto.CarouselParamDTO;
import com.newland.mall.recommend.service.CarouselService;
import com.newland.mall.validator.Insert;
import com.newland.mall.validator.Update;
import com.newland.mybatis.page.PageEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器
 *
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
@RestController
@RequestMapping("/admin/carousel")
@Tag(name = "", description = "")
public class CarouselAdminController {
    @Autowired
    private CarouselService carouselService;

    /**
     * 列表
     */
    @Parameters({
            @Parameter(name = "pageNo", description = "页码"),
            @Parameter(name = "pageSize", description = "每页条数")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Operation(method = "轮播图列表", description = "轮播图列表")
    public RestResponse<PageInfo<Carousel>> list(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize) {
        PageEntity pageEntity = new PageEntity();
        pageEntity.setPageNo(pageNo);
        pageEntity.setPageSize(pageSize);
        return RestResponse.success(carouselService.getCarouselPage(pageEntity));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(method = "新增轮播图", description = "新增轮播图")
    public RestResponse add(@RequestBody @Validated(Insert.class) CarouselParamDTO carouselParamDTO) {
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(carouselParamDTO, carousel);
        carouselService.saveCarousel(carousel);
        return RestResponse.success("操作成功");
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @Operation(method = "修改轮播图信息", description = "修改轮播图信息")
    public RestResponse update(@RequestBody @Validated(Update.class) CarouselParamDTO carouselParamDTO) {
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(carouselParamDTO, carousel);
        carouselService.updateCarousel(carousel);
        return RestResponse.success("操作成功");
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @Operation(method = "获取单条轮播图信息", description = "根据id查询")
    public RestResponse info(@PathVariable("id") Long id) {
        Carousel carousel = carouselService.getCarouselById(id);
        return RestResponse.success(carousel);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/batchDelete", method = RequestMethod.DELETE)
    @Operation(method = "批量删除轮播图信息", description = "批量删除轮播图信息")
    public RestResponse delete(@RequestBody BatchIdDTO batchIdDTO) {
        carouselService.deleteBatch(batchIdDTO.getIds());
        return RestResponse.success("操作成功");
    }
}