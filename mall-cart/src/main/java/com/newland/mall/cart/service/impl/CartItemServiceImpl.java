package com.newland.mall.cart.service.impl;

import com.github.pagehelper.PageInfo;
import com.newland.mall.cart.entity.CartItem;
import com.newland.mall.cart.mapper.CartItemMapper;
import com.newland.mall.cart.model.dto.CartItemDTO;
import com.newland.mall.cart.model.dto.CartItemPageDTO;
import com.newland.mall.cart.model.dto.UpdateCartItemDTO;
import com.newland.mall.cart.model.vo.CartItemVO;
import com.newland.mall.cart.service.CartItemService;
import com.newland.mall.model.RestResponse;
import com.newland.mall.product.ProductApiAgent;
import com.newland.mall.product.dto.ProductVO;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.page.PageWrapper;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  服务实现类
 * @author leellun
 * @since 2023-02-23 23:30:31
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {
    @Autowired
    private ProductApiAgent productApiAgent;
    @Override
    public void addCartItem(CartItemDTO saveCartItemParam, Long userId) {
        CartItem temp = baseMapper.getByUserIdAndGoodsId(userId, saveCartItemParam.getGoodsId());
        AssertUtil.isNull(temp,"已存在！无需重复添加！");
        RestResponse<ProductVO> goodsDetailResult = productApiAgent.getGoodsDetail(saveCartItemParam.getGoodsId());
        AssertUtil.isTrue(goodsDetailResult.getCode()==200,"商品不存在");
        //超出单个商品的最大数量
        AssertUtil.isNotTrue(saveCartItemParam.getGoodsCount()>=1&&saveCartItemParam.getGoodsCount()<=5,"购物车商品数量异常");

        Long totalItem = baseMapper.countByUserId(userId);
        //超出最大数量
        AssertUtil.isNotTrue(totalItem > 20,"购物车商品数量超出最大数量");
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(saveCartItemParam, cartItem);
        cartItem.setUserId(userId);
        //保存记录
        baseMapper.insertSelective(cartItem);
    }

    @Override
    public void updateCartItem(UpdateCartItemDTO updateCartItemParam, Long userId) {
        CartItem cartItemUpdate = baseMapper.selectByPrimaryKey(updateCartItemParam.getCartItemId());
        AssertUtil.notNull(cartItemUpdate,"数据不存在");
        AssertUtil.isTrue(cartItemUpdate.getUserId().equals(userId),"禁止该操作！");
        AssertUtil.isTrue(updateCartItemParam.getGoodsCount() <= 5,"超出单个商品的最大购买数量！");

        cartItemUpdate.setGoodsCount(updateCartItemParam.getGoodsCount());
        cartItemUpdate.setUpdateTime(LocalDateTime.now());
        //修改记录
        baseMapper.updateByPrimaryKeySelective(cartItemUpdate);
    }

    @Override
    public void deleteCartItem(Long shoppingCartItemId, Long userId) {
        CartItem cartItemUpdate = baseMapper.selectByPrimaryKey(shoppingCartItemId);
        AssertUtil.isTrue(cartItemUpdate.getUserId().equals(userId),"禁止该操作！");
        baseMapper.deleteByPrimaryKey(shoppingCartItemId);
    }

    @Override
    public List<CartItemVO> getMyShoppingCartItems(Long userId) {
        PageInfo<CartItem> pageInfo = PageWrapper.page(1,20,()->baseMapper.listCartItems(userId));
        List<CartItem> cartItems = pageInfo.getList();
        return getCartItemVOS(cartItems);
    }

    @Override
    public List<CartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long userId) {
        AssertUtil.isNotTrue(CollectionUtils.isEmpty(cartItemIds),"购物项不能为空");
        List<CartItem> cartItems = baseMapper.listByUserIdAndCartIds(userId, cartItemIds);
        AssertUtil.isNotTrue(CollectionUtils.isEmpty(cartItems),"购物项不能为空");
        AssertUtil.isNotTrue(cartItems.size() != cartItemIds.size(),"参数异常");
        List<CartItemVO> itemsForSettle= getCartItemVOS(cartItems);
        AssertUtil.isNotTrue(CollectionUtils.isEmpty(itemsForSettle),"参数异常");
        int priceTotal = 0;
        //总价
        for (CartItemVO cartItemVO : itemsForSettle) {
            priceTotal += cartItemVO.getGoodsCount() * cartItemVO.getSellingPrice();
        }
        AssertUtil.isNotTrue(priceTotal < 1,"价格异常");
        return itemsForSettle;
    }

    @Override
    public List<CartItem> getCartItemsByCartIds(List<Long> cartItemIds) {
        return baseMapper.listByCartItemIds(cartItemIds);
    }

    @Override
    public void deleteCartItemsByCartIds(List<Long> cartItemIds) {
        baseMapper.deleteBatch(cartItemIds);
    }

    @Override
    public PageInfo<CartItemVO> getCartItemPageList(CartItemPageDTO cartItemPageDTO) {
        PageInfo<CartItem> pageInfo = PageWrapper.page(cartItemPageDTO,()->baseMapper.listCartItems(cartItemPageDTO.getUserId()));
        PageInfo<CartItemVO> result=new PageInfo<>();
        result.setPageNum(pageInfo.getPageNum());
        result.setPages(pageInfo.getPages());
        result.setEndRow(pageInfo.getEndRow());
        result.setPageSize(pageInfo.getPageSize());
        result.setSize(pageInfo.getSize());
        result.setList(getCartItemVOS(pageInfo.getList()));
        return result;
    }

    /**
     * 数据转换
     *
     * @param cartItems
     * @return
     */
    private List<CartItemVO> getCartItemVOS(List<CartItem> cartItems) {
        List<CartItemVO> cartItemVOS=new ArrayList<>();
        if (!CollectionUtils.isEmpty(cartItems)) {
            //查询商品信息并做数据转换
            List<Long> goodsIds = cartItems.stream().map(CartItem::getGoodsId).collect(Collectors.toList());
            RestResponse<List<ProductVO>> dtoResult = productApiAgent.listByGoodsIds(goodsIds);
            //商品为空
            AssertUtil.isTrue(dtoResult.getCode()==200,"商品不存在");
            Map<Long, ProductVO> productDTOMap = new HashMap<>();
            List<ProductVO> mallProductVOs = dtoResult.getData();
            if (!CollectionUtils.isEmpty(mallProductVOs)) {
                productDTOMap = mallProductVOs.stream().collect(Collectors.toMap(ProductVO::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (CartItem cartItem : cartItems) {
                CartItemVO cartItemVO = new CartItemVO();
                BeanUtils.copyProperties(cartItem, cartItemVO);
                if (productDTOMap.containsKey(cartItem.getGoodsId())) {
                    ProductVO productVO = productDTOMap.get(cartItem.getGoodsId());
                    cartItemVO.setGoodsCoverImg(productVO.getGoodsCoverImg());
                    String goodsName = productVO.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    cartItemVO.setGoodsName(goodsName);
                    cartItemVO.setSellingPrice(productVO.getSellingPrice());
                    cartItemVOS.add(cartItemVO);
                }
            }
        }
        return cartItemVOS;
    }
}