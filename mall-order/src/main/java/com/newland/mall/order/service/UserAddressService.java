package com.newland.mall.order.service;

import com.newland.mall.order.entity.UserAddress;
import com.newland.mall.order.model.vo.UserAddressVO;
import com.newland.mybatis.service.IService;

import java.util.List;

/**
 * 收货地址表 服务类
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
public interface UserAddressService extends IService<UserAddress> {
    /**
     * 获取我的收货地址
     *
     * @param userId
     * @return
     */
    List<UserAddressVO> getMyAddresses(Long userId);

    /**
     * 保存收货地址
     *
     * @param mallUserAddress
     * @return
     */
    void saveUserAddress(UserAddress mallUserAddress);

    /**
     * 修改收货地址
     *
     * @param mallUserAddress
     * @return
     */
    void updateUserAddress(UserAddress mallUserAddress);

    /**
     * 获取收货地址详情
     *
     * @param addressId
     * @return
     */
    UserAddress getUserAddressById(Long addressId);

    /**
     * 获取我的默认收货地址
     *
     * @param userId
     * @return
     */
    UserAddress getMyDefaultAddressByUserId(Long userId);

    /**
     * 删除收货地址
     *
     * @param userId
     * @param addressId
     * @return
     */
    void deleteById(Long userId,Long addressId);
}