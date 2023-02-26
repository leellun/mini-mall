package com.newland.mall.system.entity;

import com.newland.mall.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色表
 * sys_role
 * @author leell
 * @date 2023-02-21 15:38:48
 */
@Data
@Schema(name ="角色表")
public class SysRole extends BaseEntity {
    /**
     * ID
     */
    @Schema(name ="ID")
    private Long id;

    /**
     * 名称
     */
    @Schema(name ="名称")
    private String name;

    /**
     * 编码
     */
    @Schema(name ="编码")
    private String code;

    /**
     * 角色级别
     */
    @Schema(name ="角色级别")
    private Integer level;
    /**
     * @see com.newland.mall.enumeration.BasicEnum
     * 状态 1 开启 0 禁用
     */
    @Schema(name ="状态 1 开启 0 禁用")
    private Integer enabled;

    /**
     * 描述
     */
    @Schema(name ="描述")
    private String description;

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