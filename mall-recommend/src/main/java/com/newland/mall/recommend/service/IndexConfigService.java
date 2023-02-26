package com.newland.mall.recommend.service;

import com.github.pagehelper.PageInfo;
import com.newland.mall.recommend.entity.IndexConfig;
import com.newland.mall.recommend.model.dto.IndexConfigListDTO;
import com.newland.mall.recommend.model.vo.IndexConfigProductsVO;
import com.newland.mybatis.service.IService;

import java.util.List;

/**
 *  服务类
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
public interface IndexConfigService extends IService<IndexConfig> {
    /**
     * 后台分页
     *
     * @param dto
     * @return
     */
    PageInfo<IndexConfig> getConfigsPage(IndexConfigListDTO dto);

    /**
     * 添加配置
     * @param indexConfig
     */
    void addIndexConfig(IndexConfig indexConfig);

    /**
     * 更新配置
     * @param indexConfig
     */
    void updateIndexConfig(IndexConfig indexConfig);

    /**
     * 获取配置
     * @param id
     * @return
     */
    IndexConfig getIndexConfigById(Long id);

    /**
     * 删除
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<IndexConfigProductsVO> getConfigGoodsesForIndex(int configType, int number);
}