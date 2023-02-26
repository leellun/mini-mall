package com.newland.mall.system.mapper;

import com.newland.mall.system.entity.SysMenu;
import com.newland.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 获取角色权限
     * @param roleIds 角色id列表
     * @return
     */
    List<String> getPermissions(@Param("roleIds") List<Long> roleIds);

    /**
     * 获取菜单
     * @param roleIds
     * @return
     */
    List<SysMenu> getMenuList(@Param("roleIds") List<Long> roleIds);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteBatchIds(@Param("ids") Collection<Long> ids);

    /**
     * 查询ids
     * @param ids
     * @return
     */
    List<SysMenu> selectBatchIds(@Param("ids") Collection<Long> ids);

    /**
     * 根据pids查询列表
     * @param pids
     * @return
     */
    List<SysMenu> selectBatchByPids(@Param("pids") Collection<Long> pids);
    /**
     * 子菜单
     * @param pid
     * @return
     */
    List<SysMenu> selectSubMenuByPid(@Param("pid") Long pid);
    /**
     * 子项目个数
     * @param pid
     * @return
     */
    Integer countDeptByPid(@Param("pid") Long pid);
}
