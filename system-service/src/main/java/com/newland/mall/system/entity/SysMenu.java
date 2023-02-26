package com.newland.mall.system.entity;

import com.newland.mall.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统菜单
 * sys_menu
 * @author leell
 * @date 2023-02-21 15:38:48
 */
@Data
@Schema(name ="系统菜单")
public class SysMenu extends BaseEntity {
    /**
     * ID
     */
    @Schema(name ="ID")
    private Long id;

    /**
     * 上级菜单ID
     */
    @Schema(name ="上级菜单ID")
    private Long pid;

    /**
     * 子菜单数目
     */
    @Schema(name ="子菜单数目")
    private Integer subCount;

    /**
     * 菜单类型 1菜单 2按钮
     */
    @Schema(name ="菜单类型 1菜单 2按钮")
    private Integer type;

    /**
     * 菜单标题
     */
    @Schema(name ="菜单标题")
    private String title;

    /**
     * 组件名称
     */
    @Schema(name ="组件名称")
    private String name;

    /**
     * 组件
     */
    @Schema(name ="组件")
    private String component;

    /**
     * 排序
     */
    @Schema(name ="排序")
    private Integer menuSort;

    /**
     * 图标
     */
    @Schema(name ="图标")
    private String icon;

    /**
     * 链接地址
     */
    @Schema(name ="链接地址")
    private String path;

    /**
     * 0 内部路由 1 内部外链 2 跳转外联
     */
    @Schema(name ="0 内部路由 1 内部外链 2 跳转外联")
    private Integer target;

    /**
     * 0禁用 1启用
     */
    @Schema(name ="0禁用 1启用")
    private Integer enabled;

    /**
     * 缓存 0 不缓存 1缓存
     */
    @Schema(name ="缓存 0 不缓存 1缓存")
    private Integer keepAlive;

    /**
     * 隐藏 0 不隐藏 1隐藏
     */
    @Schema(name ="隐藏 0 不隐藏 1隐藏")
    private Integer hidden;

    /**
     * 权限
     */
    @Schema(name ="权限")
    private String permission;

    /**
     * 创建者
     */
    @Schema(name ="创建者")
    private Long createdBy;

    /**
     * 修改者
     */
    @Schema(name ="修改者")
    private Long updatedBy;

    /**
     * 创建时间
     */
    @Schema(name ="创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Schema(name ="修改时间")
    private LocalDateTime updateTime;
}