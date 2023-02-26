package com.newland.mall.system.mapper;

import com.newland.mall.system.entity.SysRoleMenu;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色菜单关联 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 批量插入
     *
     * @param list
     */
    public void insertBatch(@Param("list") List<SysRoleMenu> list);

    /**
     * 根据角色id获取菜单权限
     *
     * @param roleId
     * @return
     */
    public List<Long> getMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色id删除绑定关系
     *
     * @param roleId
     * @return
     */
    public Integer deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 删除绑定关系
     *
     * @param roleId  角色id
     * @param menuIds 菜单ids
     * @return
     */
    public Integer deleteByRoleAndMenus(@Param("roleId") Long roleId,@Param("menuIds") List<Long> menuIds);
}
