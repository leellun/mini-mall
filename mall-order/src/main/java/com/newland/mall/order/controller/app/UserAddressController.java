package com.newland.mall.order.controller.app;

import com.newland.mall.model.LoginUser;
import com.newland.mall.model.RestResponse;
import com.newland.mall.order.entity.UserAddress;
import com.newland.mall.order.model.dto.UserAddressParamDTO;
import com.newland.mall.order.model.vo.UserAddressVO;
import com.newland.mall.order.service.UserAddressService;
import com.newland.security.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收货地址表 控制器
 *
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@RestController
@RequestMapping("/userAddress")
@Tag(name = "收货地址表", description = "收货地址表")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    @Parameters({
            @Parameter(name = "orderNo", description = "订单号"),
    })
    @GetMapping("/address")
    @Operation(method = "我的收货地址列表", description = "")
    public RestResponse<List<UserAddressVO>> addressList() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        return RestResponse.success(userAddressService.getMyAddresses(loginUser.getUserId()));
    }

    @PostMapping("/address")
    @Operation(method = "添加地址", description = "")
    public RestResponse saveUserAddress(@RequestBody UserAddressParamDTO userAddressParamDTO) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressParamDTO, userAddress);
        userAddress.setUserId(loginUser.getUserId());
        userAddressService.saveUserAddress(userAddress);
        //添加失败
        return RestResponse.success("添加成功");
    }

    @PutMapping("/address")
    @Operation(method = "修改地址", description = "")
    public RestResponse<Boolean> updateMallUserAddress(@RequestBody UserAddressParamDTO userAddressParamDTO) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressParamDTO, userAddress);
        userAddress.setUserId(loginUser.getUserId());
        userAddressService.updateUserAddress(userAddress);
        return RestResponse.success("修改成功");
    }

    @GetMapping("/address/{addressId}")
    @Operation(method = "获取收货地址详情", description = "传参为地址id")
    public RestResponse<UserAddress> getUserAddress(@PathVariable("addressId") Long addressId) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        UserAddress userAddress = userAddressService.getUserAddressById(addressId);
        return RestResponse.success(userAddress);
    }

    @GetMapping("/address/default")
    @Operation(method = "获取默认收货地址", description = "无传参")
    public RestResponse getDefaultUserAddress() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        UserAddress userAddress = userAddressService.getMyDefaultAddressByUserId(loginUser.getUserId());
        return RestResponse.success(userAddress);
    }

    @DeleteMapping("/address/{addressId}")
    @Operation(method = "删除收货地址", description = "传参为地址id")
    public RestResponse deleteAddress(@PathVariable("addressId") Long addressId) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        userAddressService.deleteById(loginUser.getUserId(), addressId);
        return RestResponse.success("删除成功");
    }
}