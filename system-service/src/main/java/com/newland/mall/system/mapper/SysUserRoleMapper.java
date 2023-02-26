package com.newland.mall.system.mapper;

import com.newland.mall.system.entity.SysUserRole;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户角色关联 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 根据角色id获取绑定用户id列表
     *
     * @param roleId
     * @return
     */
    public List<Long> getUserIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色id删除绑定关系
     *
     * @param roleId
     * @return
     */
    public Integer deleteByRoleId(@Param("roleId") Long roleId);
    /**
     * 根据用户id删除绑定关系
     *
     * @param userIds
     * @return
     */
    public Integer deleteByUserIds(@Param("userIds") Collection<Long> userIds);

    /**
     * 根据用户id删除
     * @param userId
     * @return
     */
    public Integer deleteByUserId(@Param("userId") Long userId);

    /**
     * 通过用户id获取角色id
     * @param userId 用户id
     * @return 角色id列表
     */
    public List<Long> selectRoleIdsByUserId(@Param("userId") Long userId);
}
