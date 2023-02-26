package com.newland.mall.order.service.impl;

import com.newland.mall.order.entity.UserAddress;
import com.newland.mall.order.mapper.UserAddressMapper;
import com.newland.mall.order.model.vo.UserAddressVO;
import com.newland.mall.order.service.UserAddressService;
import com.newland.mall.utils.AssertUtil;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 收货地址表 服务实现类
 *
 * @author leellun
 * @since 2023-02-25 14:07:25
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {
    @Override
    public List<UserAddressVO> getMyAddresses(Long userId) {
        List<UserAddress> myAddressList = baseMapper.listByUserId(userId);
        List<UserAddressVO> UserAddressVOS = myAddressList.stream().map(item -> {
            UserAddressVO vo = new UserAddressVO();
            BeanUtils.copyProperties(item, vo);
            return vo;
        }).collect(Collectors.toList());
        return UserAddressVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserAddress(UserAddress userAddress) {
        if (userAddress.getDefaultFlag().intValue() == 1) {
            //添加默认地址，需要将原有的默认地址修改掉
            UserAddress defaultAddress = baseMapper.getMyDefaultAddressByUserId(userAddress.getUserId());
            if (defaultAddress != null) {
                defaultAddress.setDefaultFlag(0);
                defaultAddress.setUpdateTime(LocalDateTime.now());
                int updateResult = baseMapper.updateByPrimaryKeySelective(defaultAddress);
                AssertUtil.isTrue(updateResult > 0, "操作失败");
            }
        }
        baseMapper.insertSelective(userAddress);
    }

    @Override
    public void updateUserAddress(UserAddress userAddress) {
        UserAddress tempAddress = getUserAddressById(userAddress.getAddressId());
        if (userAddress.getDefaultFlag().intValue() == 1) {
            //修改为默认地址，需要将原有的默认地址修改掉
            UserAddress defaultAddress = baseMapper.getMyDefaultAddressByUserId(userAddress.getUserId());
            if (defaultAddress != null && !defaultAddress.getAddressId().equals(tempAddress)) {
                //存在默认地址且默认地址并不是当前修改的地址
                defaultAddress.setDefaultFlag(0);
                defaultAddress.setUpdateTime(LocalDateTime.now());
                int updateResult = baseMapper.updateByPrimaryKeySelective(defaultAddress);
                AssertUtil.isTrue(updateResult > 0, "操作失败");
            }
        }
        userAddress.setUpdateTime(LocalDateTime.now());
        baseMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    public UserAddress getUserAddressById(Long addressId) {
        UserAddress userAddress = baseMapper.selectByPrimaryKey(addressId);
        AssertUtil.notNull(userAddress, "地址不存在");
        return userAddress;
    }

    @Override
    public UserAddress getMyDefaultAddressByUserId(Long userId) {
        return baseMapper.getMyDefaultAddressByUserId(userId);
    }

    @Override
    public void deleteById(Long userId, Long addressId) {
        UserAddress userAddress = baseMapper.selectByPrimaryKey(addressId);
        AssertUtil.notNull(userAddress, "地址不存在");
        AssertUtil.isTrue(userId.equals(userAddress.getUserId()), "无权限操作");
        baseMapper.deleteByPrimaryKey(addressId);
    }
}