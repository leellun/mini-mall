package com.newland.mall.recommend.service.impl;

import com.github.pagehelper.PageInfo;
import com.newland.mall.recommend.entity.Carousel;
import com.newland.mall.recommend.mapper.CarouselMapper;
import com.newland.mall.recommend.model.vo.IndexCarouselVO;
import com.newland.mall.recommend.service.CarouselService;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.page.PageEntity;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务实现类
 *
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {
    @Override
    public PageInfo<Carousel> getCarouselPage(PageEntity pageEntity) {
        return PageWrapper.page(pageEntity, () -> baseMapper.getCarouselList());
    }

    @Override
    public void saveCarousel(Carousel carousel) {
        baseMapper.insertSelective(carousel);
    }

    @Override
    public void updateCarousel(Carousel carousel) {
        Carousel temp = baseMapper.selectByPrimaryKey(carousel.getCarouselId());
        AssertUtil.notNull(temp, "数据不存在");
        temp.setCarouselRank(carousel.getCarouselRank());
        temp.setRedirectUrl(carousel.getRedirectUrl());
        temp.setCarouselUrl(carousel.getCarouselUrl());
        temp.setUpdateTime(LocalDateTime.now());
        baseMapper.updateByPrimaryKeySelective(temp);
    }

    @Override
    public Carousel getCarouselById(Long id) {
        Carousel carousel= baseMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(carousel,"数据不存在");
        return carousel;
    }

    @Override
    public void deleteBatch(Long[] ids) {
        AssertUtil.isTrue(ids.length >= 0, "操作异常");
        //删除数据
        int count = baseMapper.deleteBatch(ids);
        AssertUtil.isTrue(count > 0, "删除失败");
    }

    @Override
    public List<IndexCarouselVO> getCarouselsForIndex(int number) {
        List<IndexCarouselVO> indexCarouselVOS = new ArrayList<>(number);
        List<Carousel> carousels = baseMapper.listCarouselsByNum(number);
        if (!CollectionUtils.isEmpty(carousels)) {
            carousels.forEach(item -> {
                IndexCarouselVO vo = new IndexCarouselVO();
                BeanUtils.copyProperties(item, vo);
                indexCarouselVOS.add(vo);
            });
        }
        return indexCarouselVOS;
    }
}