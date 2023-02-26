package com.newland.mall.order.mapper;

import com.newland.mall.order.entity.UserAddress;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收货地址表 Mapper 接口
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Repository
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    /**
     * 根据用户获取地址列表
     * @param userId
     * @return
     */
    List<UserAddress> listByUserId(@Param("userId") Long userId);
    /**
     * 获取我的默认收货地址
     *
     * @param userId
     * @return
     */
    UserAddress getMyDefaultAddressByUserId(@Param("userId") Long userId);
}