package com.newland.mall.cart.agent;

import com.newland.mall.cart.vo.CartItemVO;
import com.newland.mall.model.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mall-cart",contextId = "cart", path = "/shop-cart")
public interface CartApiAgent {

    @GetMapping(value = "/listByCartItemIds")
    RestResponse<List<CartItemVO>> listByCartItemIds(@RequestParam(value = "cartItemIds") List<Long> cartItemIds);

    @DeleteMapping(value = "/deleteByCartItemIds")
    RestResponse deleteByCartItemIds(@RequestParam(value = "cartItemIds") List<Long> cartItemIds);
}
