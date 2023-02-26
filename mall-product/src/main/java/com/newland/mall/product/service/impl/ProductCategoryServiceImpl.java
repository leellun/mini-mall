package com.newland.mall.product.service.impl;

import com.github.pagehelper.PageInfo;
import com.newland.mall.exception.BusinessException;
import com.newland.mall.product.common.ProductCategoryLevelEnum;
import com.newland.mall.product.entity.ProductCategory;
import com.newland.mall.product.mapper.ProductCategoryMapper;
import com.newland.mall.product.model.dto.ProductCateListDTO;
import com.newland.mall.product.model.vo.IndexCategoryVO;
import com.newland.mall.product.model.vo.ProductCateSelectItemVO;
import com.newland.mall.product.model.vo.ProductCateSelectListVO;
import com.newland.mall.product.service.ProductCategoryService;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * 服务实现类
 *
 * @author leellun
 * @since 2023-02-24 10:30:33
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {
    @Override
    public void addCategory(ProductCategory productCategory) {
        ProductCategory temp = baseMapper.getByParentIdAndName(productCategory.getParentId(), productCategory.getCategoryName());
        AssertUtil.isNull(temp, "分类存在");
        setCategoryLevel(productCategory);
        baseMapper.insertSelective(productCategory);
    }

    @Override
    public void updateCategory(ProductCategory productCategory) {
        ProductCategory temp = baseMapper.selectByPrimaryKey(productCategory.getCategoryId());
        AssertUtil.notNull(temp, "分类不存在");
        ProductCategory temp2 = baseMapper.getByParentIdAndName(productCategory.getParentId(), productCategory.getCategoryName());
        if (temp2 != null && !temp2.getCategoryId().equals(productCategory.getCategoryId())) {
            //同名且不同id 不能继续修改
            throw new BusinessException("分类存在");
        }
        setCategoryLevel(productCategory);
        productCategory.setUpdateTime(LocalDateTime.now());
        baseMapper.updateByPrimaryKeySelective(productCategory);
    }

    /**
     * 设置分类等级
     *
     * @param productCategory
     */
    private void setCategoryLevel(ProductCategory productCategory) {
        if (productCategory.getParentId() != null && productCategory.getParentId() != 0) {
            ProductCategory parent = baseMapper.selectByPrimaryKey(productCategory.getParentId());
            AssertUtil.notNull(parent, "父类不存在");
            AssertUtil.isNotTrue(parent.getCategoryLevel() < 3, "只能创建三级分类");
            productCategory.setCategoryLevel(parent.getCategoryLevel() + 1);
        } else {
            productCategory.setCategoryLevel(1);
        }
    }

    @Override
    public void deleteBatch(Long[] ids) {
        AssertUtil.isTrue(ids.length > 0, "操作异常！");
        //删除分类数据
        int count = baseMapper.deleteBatch(ids);
        AssertUtil.isTrue(count > 0, "删除失败！");
    }

    @Override
    public PageInfo<ProductCategory> getCategoryPage(ProductCateListDTO dto) {
        return PageWrapper.page(dto, () -> baseMapper.getCategoryList(dto));
    }

    @Override
    public List<IndexCategoryVO> getCategoriesForIndex() {
        List<IndexCategoryVO> indexCategoryVOS = new ArrayList<>();
        //获取一级分类的固定数量的数据
        List<ProductCategory> firstLevelCategories = baseMapper.getByLevelAndParentIdsAndNumber(Collections.singletonList(0L), ProductCategoryLevelEnum.LEVEL_ONE.getLevel(), 10);
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<Long> firstLevelCategoryIds = firstLevelCategories.stream().map(ProductCategory::getCategoryId).collect(Collectors.toList());
            //获取二级分类的数据
            List<ProductCategory> secondLevelCategories = baseMapper.getByLevelAndParentIdsAndNumber(firstLevelCategoryIds, ProductCategoryLevelEnum.LEVEL_TWO.getLevel(), 0);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                List<Long> secondLevelCategoryIds = secondLevelCategories.stream().map(ProductCategory::getCategoryId).collect(Collectors.toList());
                //获取三级分类的数据
                List<ProductCategory> thirdLevelCategories = baseMapper.getByLevelAndParentIdsAndNumber(secondLevelCategoryIds, ProductCategoryLevelEnum.LEVEL_THREE.getLevel(), 0);
                if (!CollectionUtils.isEmpty(thirdLevelCategories)) {
                    //根据 parentId 将 thirdLevelCategories 分组
                    Map<Long, List<ProductCategory>> thirdLevelCategoryMap = thirdLevelCategories.stream().collect(groupingBy(ProductCategory::getParentId));
                    List<IndexCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
                    //处理二级分类
                    for (ProductCategory secondLevelCategory : secondLevelCategories) {
                        IndexCategoryVO secondLevelCategoryVO = new IndexCategoryVO();
                        BeanUtils.copyProperties(secondLevelCategory, secondLevelCategoryVO);
                        //如果该二级分类下有数据则放入 secondLevelCategoryVOS 对象中
                        if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getCategoryId())) {
                            //根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                            List<ProductCategory> tempGoodsCategories = thirdLevelCategoryMap.get(secondLevelCategory.getCategoryId());
                            List<IndexCategoryVO> childrens = new ArrayList<>();
                            tempGoodsCategories.forEach(item -> {
                                IndexCategoryVO vo = new IndexCategoryVO();
                                BeanUtils.copyProperties(item, vo);
                                childrens.add(vo);
                            });
                            secondLevelCategoryVO.setChildren(childrens);
                            secondLevelCategoryVOS.add(secondLevelCategoryVO);
                        }
                    }
                    //处理一级分类
                    if (!CollectionUtils.isEmpty(secondLevelCategoryVOS)) {
                        //根据 parentId 将 thirdLevelCategories 分组
                        Map<Long, List<IndexCategoryVO>> secondLevelCategoryVOMap = secondLevelCategoryVOS.stream().collect(groupingBy(IndexCategoryVO::getParentId));
                        for (ProductCategory firstCategory : firstLevelCategories) {
                            IndexCategoryVO indexCategoryVO = new IndexCategoryVO();
                            BeanUtils.copyProperties(firstCategory, indexCategoryVO);
                            //如果该一级分类下有数据则放入 indexCategoryVOS 对象中
                            if (secondLevelCategoryVOMap.containsKey(firstCategory.getCategoryId())) {
                                //根据一级分类的id取出secondLevelCategoryVOMap分组中的二级级分类list
                                List<IndexCategoryVO> tempGoodsCategories = secondLevelCategoryVOMap.get(firstCategory.getCategoryId());
                                indexCategoryVO.setChildren(tempGoodsCategories);
                                indexCategoryVOS.add(indexCategoryVO);
                            }
                        }
                    }
                }
            }
            return indexCategoryVOS;
        } else {
            return null;
        }
    }

    @Override
    public ProductCateSelectListVO getCategoryListForSelect(Long categoryId) {
        ProductCategory category = baseMapper.selectByPrimaryKey(categoryId);
        //既不是一级分类也不是二级分类则为不返回数据
        if (category == null || category.getCategoryLevel() == ProductCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            throw new BusinessException("参数异常！");
        }
        ProductCateSelectListVO vo = new ProductCateSelectListVO();
        if (category.getCategoryLevel() == ProductCategoryLevelEnum.LEVEL_ONE.getLevel()) {
            //如果是一级分类则返回当前一级分类下的所有二级分类，以及二级分类列表中第一条数据下的所有三级分类列表
            //查询一级分类列表中第一个实体的所有二级分类
            List<ProductCategory> secondLevelCategories = baseMapper.getByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), ProductCategoryLevelEnum.LEVEL_TWO.getLevel(), 0);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<ProductCategory> thirdLevelCategories = baseMapper.getByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), ProductCategoryLevelEnum.LEVEL_THREE.getLevel(), 0);
                vo.setSecondLevelCategories(secondLevelCategories);
                vo.setThirdLevelCategories(thirdLevelCategories);
            }
        } else if (category.getCategoryLevel() == ProductCategoryLevelEnum.LEVEL_TWO.getLevel()) {
            //如果是二级分类则返回当前分类下的所有三级分类列表
            List<ProductCategory> thirdLevelCategories = baseMapper.getByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), ProductCategoryLevelEnum.LEVEL_THREE.getLevel(), 0);
            vo.setThirdLevelCategories(thirdLevelCategories);
        }
        return vo;
    }

    @Override
    public List<ProductCateSelectItemVO> getAllCategoryListForSelect() {
        List<ProductCateSelectItemVO> list = baseMapper.selectAllByRank();
        List<ProductCateSelectItemVO> categorys = new ArrayList<>();
        Map<Long, ProductCateSelectItemVO> map = new HashMap<>();
        Map<Long, List<ProductCateSelectItemVO>> childrenMap = new HashMap<>();
        for (ProductCateSelectItemVO vo : list) {
            if (vo.getParentId() == null || vo.getParentId() == 0) {
                categorys.add(vo);
            }
            if (childrenMap.get(vo.getCategoryId()) != null) {
                vo.setChildren(childrenMap.get(vo.getCategoryId()));
            }
            if (childrenMap.get(vo.getCategoryId()) == null) {
                childrenMap.put(vo.getCategoryId(), new ArrayList<>());
            }
            map.put(vo.getCategoryId(), vo);
            List<ProductCateSelectItemVO> slibingItems = childrenMap.get(vo.getParentId());
            if (slibingItems == null) {
                slibingItems = new ArrayList<>();
                childrenMap.put(vo.getParentId(), slibingItems);
                if (map.get(vo.getParentId()) != null) {
                    map.get(vo.getParentId()).setChildren(slibingItems);
                }
            }
            slibingItems.add(vo);
        }

        return categorys;
    }
}