package com.newland.mall.member.service;

import com.github.pagehelper.PageInfo;
import com.newland.mall.member.entity.User;
import com.newland.mall.member.model.dto.UserPageDTO;
import com.newland.mall.member.model.dto.UserUpdateDTO;
import com.newland.mall.member.model.vo.UserVO;
import com.newland.mall.model.MallUser;
import com.newland.mybatis.page.PageEntity;
import com.newland.mybatis.service.IService;

/**
 *  服务类
 * @author leellun
 * @since 2023-02-23 19:43:27
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param loginName
     * @param password
     * @return
     */
    void register(String loginName, String password);


    /**
     * 登录
     *
     * @param loginName
     * @param passwordMD5
     * @return
     */
    MallUser login(String loginName, String passwordMD5);

    /**
     * 用户信息修改
     *
     * @param mallUser
     * @return
     */
    void updateUserInfo(UserUpdateDTO mallUser, Long userId);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    void lockUsers(Long[] ids, int lockStatus);

    /**
     * 后台分页
     *
     * @param userPageDTO
     * @return
     */
    PageInfo<User> getUsersPage(UserPageDTO userPageDTO);

    /**
     * 获取用户详情
     * @param userId
     * @return
     */
    UserVO getUserDetail(Long userId);
}