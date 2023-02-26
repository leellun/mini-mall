package com.newland.mall.product;

import com.newland.mall.model.RestResponse;
import com.newland.mall.product.dto.ProductVO;
import com.newland.mall.product.dto.UpdateStockNumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mall-product", path = "/admin/product")
public interface ProductApiAgent {
    @GetMapping(value = "/goodsDetail")
    RestResponse<ProductVO> getGoodsDetail(@RequestParam(value = "goodsId") Long goodsId);

    @GetMapping(value = "/listByGoodsIds")
    RestResponse<List<ProductVO>> listByGoodsIds(@RequestParam(value = "goodsIds") List<Long> goodsIds);

    @PutMapping(value = "/updateStock")
    RestResponse updateStock(@RequestBody UpdateStockNumDTO updateStockNumDTO);
}
