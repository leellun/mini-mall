package com.newland.mall.member.service.impl;

import com.newland.mall.member.entity.User;
import com.newland.mall.member.mapper.UserMapper;
import com.newland.mall.member.service.UserService;
import com.newland.mybatis.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 * @author leellun
 * @since 2023-02-23 19:43:27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}