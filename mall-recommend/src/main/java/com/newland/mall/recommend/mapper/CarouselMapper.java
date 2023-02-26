package com.newland.mall.recommend.mapper;

import com.newland.mall.recommend.entity.Carousel;
import com.newland.mybatis.mapper.BaseMapper;
import com.newland.mybatis.page.PageEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Mapper 接口
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
@Repository
public interface CarouselMapper extends BaseMapper<Carousel> {
    List<Carousel> getCarouselList();
    int deleteBatch(@Param("ids") Long[] ids);

    List<Carousel> listCarouselsByNum(@Param("number") int number);
}