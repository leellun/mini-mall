package com.newland.mall.system.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色菜单关联
 * sys_role_menu
 * @author leell
 * @date 2023-02-21 15:38:48
 */
@Data
@Schema(name ="角色菜单关联")
public class SysRoleMenu {
    /**
     * 主键
     */
    @Schema(name ="主键")
    private Long id;

    /**
     * 菜单ID
     */
    @Schema(name ="菜单ID")
    private Long menuId;

    /**
     * 角色ID
     */
    @Schema(name ="角色ID")
    private Long roleId;
    public SysRoleMenu(Long roleId, Long menuId){
        this.roleId=roleId;
        this.menuId=menuId;
    }
}