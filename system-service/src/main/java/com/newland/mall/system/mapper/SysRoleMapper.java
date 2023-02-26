package com.newland.mall.system.mapper;

import com.newland.mall.system.entity.SysRole;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2022-12-06
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取用户角色
     * @param userId 用户id
     * @return
     */
    List<SysRole> getRoleWithIdByUserId(@Param("userId") Long userId,@Param("enabled")Integer enabled);
    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteBatchIds(@Param("ids") Collection<Long> ids);

    /**
     * 通过name和enabled模糊查询
     * @param name
     * @param enabled
     * @return
     */
    List<SysRole> getRoleByNameAndEnabled(@Param("name") String name,@Param("enabled")Integer enabled);

    /**
     * 查询角色通过name
     * @param name
     * @return
     */
    SysRole getRoleByName(@Param("name") String name);
    /**
     * 查询角色
     * @param code
     * @return
     */
    SysRole getRoleByCode(@Param("code") String code);

    /**
     * 根据角色id列表查询角色
     * @param ids 角色id 列表
     * @return 角色列表
     */
    List<SysRole> selectByIds(@Param("ids") List<Long> ids);
}
