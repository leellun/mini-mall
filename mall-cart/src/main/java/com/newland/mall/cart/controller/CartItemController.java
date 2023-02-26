package com.newland.mall.cart.controller;

import com.github.pagehelper.PageInfo;
import com.newland.mall.cart.entity.CartItem;
import com.newland.mall.cart.model.dto.CartItemDTO;
import com.newland.mall.cart.model.dto.CartItemPageDTO;
import com.newland.mall.cart.model.dto.UpdateCartItemDTO;
import com.newland.mall.cart.model.vo.CartItemVO;
import com.newland.mall.cart.service.CartItemService;
import com.newland.mall.model.RestResponse;
import com.newland.security.utils.SecurityHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  控制器
 * @author leellun
 * @since 2023-02-23 23:30:31
 */
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/cartItem")
@Tag(name = "", description = "")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private SecurityHelper securityHelper;

    @Operation(method ="购物车列表(每页默认5条)", description = "传参为页码")
    @GetMapping("/shop-cart/page")
    public RestResponse<PageInfo<CartItemVO>> cartItemPageList(Integer pageNumber) {
        CartItemPageDTO cartItemPageDTO=new CartItemPageDTO();
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        cartItemPageDTO.setPageNo(pageNumber);
        cartItemPageDTO.setPageSize(5);
        cartItemPageDTO.setUserId(securityHelper.getMallUser().getUserId());
        return RestResponse.success(cartItemService.getCartItemPageList(cartItemPageDTO));
    }

    @Operation(method ="购物车列表(网页移动端不分页)", description = "")
    @GetMapping("/shop-cart")
    public RestResponse<List<CartItemVO>> cartItemList() {
        return RestResponse.success(cartItemService.getMyShoppingCartItems(securityHelper.getMallUser().getUserId()));
    }

    @PostMapping("/shop-cart")
    @Operation(method ="添加商品到购物车接口", description = "传参为商品id、数量")
    public RestResponse addCartItem(@RequestBody CartItemDTO cartItemDTO) {
        cartItemService.addCartItem(cartItemDTO,securityHelper.getMallUser().getUserId());
        return RestResponse.success("添加成功");
    }

    @PutMapping("/shop-cart")
    @Operation(method ="修改购物项数据", description = "传参为购物项id、数量")
    public RestResponse updateCartItem(@RequestBody UpdateCartItemDTO updateCartItemDTO) {
        cartItemService.updateCartItem(updateCartItemDTO,securityHelper.getMallUser().getUserId());
        return RestResponse.success("修改成功");
    }

    @DeleteMapping("/shop-cart/{cartItemId}")
    @Operation(method ="删除购物项", description = "传参为购物项id")
    public RestResponse deleteCartItem(@PathVariable("cartItemId") Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId,securityHelper.getMallUser().getUserId());
        return RestResponse.success("删除成功");
    }

    @GetMapping("/shop-cart/settle")
    @Operation(method ="根据购物项id数组查询购物项明细", description = "确认订单页面使用")
    public RestResponse<List<CartItemVO>> toSettle(Long[] cartItemIds) {
        if (cartItemIds.length < 1) {
            RestResponse.error("参数异常");
        }
        List<CartItemVO> list= cartItemService.getCartItemsForSettle(Arrays.asList(cartItemIds),securityHelper.getMallUser().getUserId());
        return RestResponse.success(list);
    }

    @GetMapping("/shop-cart/listByCartItemIds")
    @Operation(method ="购物车列表", description = "")
    public RestResponse<List<CartItem>> cartItemListByIds(@RequestParam("cartItemIds") List<Long> cartItemIds) {
        if (CollectionUtils.isEmpty(cartItemIds)) {
            return RestResponse.error("参数异常");
        }
        return RestResponse.success(cartItemService.getCartItemsByCartIds(cartItemIds));
    }

    @DeleteMapping("/shop-cart/deleteByCartItemIds")
    @Operation(method ="批量删除购物项", description = "")
    public RestResponse deleteByCartItemIds(@RequestParam("cartItemIds") List<Long> cartItemIds) {
        if (CollectionUtils.isEmpty(cartItemIds)) {
            return RestResponse.error("error param");
        }
        cartItemService.deleteCartItemsByCartIds(cartItemIds);
        return RestResponse.success("删除成功");
    }
}