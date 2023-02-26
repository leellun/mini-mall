package com.newland.mall.system.mapper;

import com.newland.mall.system.entity.SysUser;
import com.newland.mall.system.model.dto.UserQueryDTO;
import com.newland.mall.system.model.vo.SysUserVo;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 分页获取用户列表
     * @param dto 查询信息
     * @return 分页列表
     */
    List<SysUserVo> selectUsersPage(@Param("dto") UserQueryDTO dto);

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    SysUser selectByUsername(@Param("username") String username);
    /**
     * 通过phone查找用户
     * @param phone
     * @return
     */
    SysUser selectByPhone(@Param("phone") String phone);
    /**
     * 通过phone查找用户
     * @param email
     * @return
     */
    SysUser selectByEmail(@Param("email") String email);

    /**
     * 通过用户id删除用户
     * @param userIds
     * @return
     */
    Integer deleteByUserIds(@Param("userIds") Collection<Long> userIds);

}
