package com.newland.mall.system.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.mall.model.LoginUser;
import com.newland.mall.system.dto.LoginDto;
import com.newland.mall.system.entity.SysUser;
import com.newland.mall.system.model.vo.SysUserItemVo;
import com.newland.mall.system.model.vo.SysUserVo;
import com.newland.mall.system.model.dto.SysUserDto;
import com.newland.mall.system.model.dto.UserPassVO;
import com.newland.mall.system.model.dto.UserQueryDTO;
import com.newland.mybatis.service.IService;

import java.util.Set;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 登录
     * @param loginDTO
     * @return
     */
    LoginUser login(LoginDto loginDTO);

    /**
     * 分页查询
     * @param userQueryDTO
     * @return
     */
    PageInfo<SysUserVo> getUsers(UserQueryDTO userQueryDTO);
    /**
     * 获取用户
     * @param userId 用户id
     * @return
     */
    SysUserItemVo getUser(Long userId);

    /**
     * 添加用户
     * @param sysUserDto
     */
    void addUser(SysUserDto sysUserDto);

    /**
     * 更新用户
     * @param sysUserDto
     */
    void updateUser(SysUserDto sysUserDto);
    /**
     * 更新用户状态
     * @param id 用户id
     * @param enable 状态
     */
    void enableUser(Long id,Integer enable);

    /**
     * 删除用户
     * @param ids
     */
    void deleteUser(Set<Long> ids);

    /**
     * 更新密码
     * @param userPassVO
     */
    void updatePass(UserPassVO userPassVO);

    /**
     * 重置密码
     */
    void resetPass(Long id);

    /**
     * 更新头像
     * @param avatar
     */
    void updateAvatar(String avatar);
}
