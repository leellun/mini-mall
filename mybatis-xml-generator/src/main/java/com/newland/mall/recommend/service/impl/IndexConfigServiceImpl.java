package com.newland.mall.recommend.service.impl;

import com.newland.mall.recommend.entity.IndexConfig;
import com.newland.mall.recommend.mapper.IndexConfigMapper;
import com.newland.mall.recommend.service.IndexConfigService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
@Service
public class IndexConfigServiceImpl extends ServiceImpl<IndexConfigMapper, IndexConfig> implements IndexConfigService {
}