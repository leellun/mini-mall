package com.newland.mall.recommend.service;

import com.github.pagehelper.PageInfo;
import com.newland.mall.recommend.entity.Carousel;
import com.newland.mall.recommend.model.vo.IndexCarouselVO;
import com.newland.mybatis.page.PageEntity;
import com.newland.mybatis.service.IService;

import java.util.List;

/**
 *  服务类
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
public interface CarouselService extends IService<Carousel> {
    /**
     * 后台分页
     *
     * @param pageEntity
     * @return
     */
    PageInfo<Carousel> getCarouselPage(PageEntity pageEntity);

    void saveCarousel(Carousel carousel);

    void updateCarousel(Carousel carousel);

    Carousel getCarouselById(Long id);

    void deleteBatch(Long[] ids);

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<IndexCarouselVO> getCarouselsForIndex(int number);
}