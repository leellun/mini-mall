package com.newland.mall.recommend.service.impl;

import com.github.pagehelper.PageInfo;
import com.newland.mall.enumeration.ResultCode;
import com.newland.mall.exception.BusinessException;
import com.newland.mall.model.RestResponse;
import com.newland.mall.product.ProductApiAgent;
import com.newland.mall.product.dto.ProductVO;
import com.newland.mall.recommend.entity.IndexConfig;
import com.newland.mall.recommend.mapper.IndexConfigMapper;
import com.newland.mall.recommend.model.dto.IndexConfigListDTO;
import com.newland.mall.recommend.model.vo.IndexConfigProductsVO;
import com.newland.mall.recommend.service.IndexConfigService;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author leellun
 * @since 2023-02-25 00:22:56
 */
@Service
public class IndexConfigServiceImpl extends ServiceImpl<IndexConfigMapper, IndexConfig> implements IndexConfigService {
    @Autowired
    private ProductApiAgent productApiAgent;

    @Override
    public PageInfo<IndexConfig> getConfigsPage(IndexConfigListDTO dto) {
        PageInfo<IndexConfig> pageInfo = PageWrapper.page(dto, () -> baseMapper.listIndexConfigsByTypeAndNum(dto.getConfigType(), dto.getPageSize()));
        return pageInfo;
    }

    @Override
    public void addIndexConfig(IndexConfig indexConfig) {
        RestResponse<ProductVO> goodsDetailResult = productApiAgent.getGoodsDetail(indexConfig.getGoodsId());
        if (goodsDetailResult == null || goodsDetailResult.getCode() != 200) {
            throw new BusinessException("商品不存在");
        }
        AssertUtil.isNull(baseMapper.getByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId()), "存在相同的配置");
        baseMapper.insertSelective(indexConfig);
    }

    @Override
    public void updateIndexConfig(IndexConfig indexConfig) {
        RestResponse<ProductVO> goodsDetailResult = productApiAgent.getGoodsDetail(indexConfig.getGoodsId());
        if (goodsDetailResult == null || goodsDetailResult.getCode() != 200) {
            throw new BusinessException("商品不存在");
        }
        IndexConfig temp = baseMapper.selectByPrimaryKey(indexConfig.getConfigId());
        AssertUtil.notNull(temp, "数据不存在");
        IndexConfig temp2 = baseMapper.getByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId());
        if (temp2 != null && !temp2.getConfigId().equals(indexConfig.getConfigId())) {
            //goodsId相同且不同id 不能继续修改
            throw new BusinessException("存在相同的配置");
        }
        indexConfig.setUpdateTime(LocalDateTime.now());
        baseMapper.updateByPrimaryKeySelective(indexConfig);
    }

    @Override
    public IndexConfig getIndexConfigById(Long id) {
        IndexConfig config = baseMapper.selectByPrimaryKey(id);
        AssertUtil.notNull(config, "不存在");
        return config;
    }

    @Override
    public void deleteBatch(Long[] ids) {
        AssertUtil.isTrue(ids.length >= 0, "操作异常");
        //删除数据
        int count = baseMapper.deleteBatch(ids);
        AssertUtil.isTrue(count > 0, "删除失败");
    }


    @Override
    public List<IndexConfigProductsVO> getConfigGoodsesForIndex(int configType, int number) {
        List<IndexConfigProductsVO> indexConfigGoodsVOS = new ArrayList<>(number);
        List<IndexConfig> indexConfigs = baseMapper.listIndexConfigsByTypeAndNum(configType, number);
        if (!CollectionUtils.isEmpty(indexConfigs)) {
            //取出所有的goodsId
            List<Long> goodsIds = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());
            RestResponse<List<ProductVO>> productDTOResult = productApiAgent.listByGoodsIds(goodsIds);
            if (!productDTOResult.getCode().equals(ResultCode.SUCCESS.getCode())) {

            }
            AssertUtil.isTrue(productDTOResult.getCode().equals(ResultCode.SUCCESS.getCode()), "获取失败");
            productDTOResult.getData().forEach(item -> {
                IndexConfigProductsVO vo = new IndexConfigProductsVO();
                BeanUtils.copyProperties(item, vo);
                indexConfigGoodsVOS.add(vo);
            });

            for (IndexConfigProductsVO indexConfigProductsVO : indexConfigGoodsVOS) {
                String goodsName = indexConfigProductsVO.getGoodsName();
                String goodsIntro = indexConfigProductsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 30) {
                    goodsName = goodsName.substring(0, 30) + "...";
                    indexConfigProductsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 22) {
                    goodsIntro = goodsIntro.substring(0, 22) + "...";
                    indexConfigProductsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        return indexConfigGoodsVOS;
    }
}