package com.newland.mall.recommend.service.impl;

import com.newland.mall.recommend.entity.Carousel;
import com.newland.mall.recommend.mapper.CarouselMapper;
import com.newland.mall.recommend.service.CarouselService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {
}