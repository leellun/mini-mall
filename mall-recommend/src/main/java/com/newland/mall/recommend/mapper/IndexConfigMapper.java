package com.newland.mall.recommend.mapper;

import com.newland.mall.recommend.entity.IndexConfig;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Mapper 接口
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
@Repository
public interface IndexConfigMapper extends BaseMapper<IndexConfig> {
    List<IndexConfig> listIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);

    /**
     * 通过配置类型和商品id获取首页配置
     * @param configType 配置类型
     * @param goodsId 商品id
     * @return
     */
    IndexConfig getByTypeAndGoodsId(@Param("configType") int configType, @Param("goodsId") Long goodsId);
    int deleteBatch(@Param("ids") Long[] ids);
}