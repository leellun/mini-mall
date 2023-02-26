package com.newland.mall.system.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户角色关联
 * sys_user_role
 *
 * @author leell
 * @date 2023-02-21 15:38:48
 */
@Data
@Schema(name = "用户角色关联")
public class SysUserRole {
    /**
     *
     */
    private Long id;

    /**
     * 用户ID
     */
    @Schema(name = "用户ID")
    private Long userId;

    /**
     * 角色ID
     */
    @Schema(name = "角色ID")
    private Long roleId;

    public SysUserRole() {
    }

    public SysUserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}