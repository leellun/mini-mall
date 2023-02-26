package com.newland.mall.order.service.impl;

import com.newland.mall.order.entity.UserAddress;
import com.newland.mall.order.mapper.UserAddressMapper;
import com.newland.mall.order.service.UserAddressService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 收货地址表 服务实现类
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {
}