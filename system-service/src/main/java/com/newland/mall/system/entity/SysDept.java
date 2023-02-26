package com.newland.mall.system.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.newland.mall.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author leellun
 * @since 2023-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name="SysDepartment对象", description="部门")
public class SysDept extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @JsonSerialize(using= ToStringSerializer.class)
    @Schema(name="上级部门")
    private Long pid;

    @Schema(name="子部门数目")
    private Integer subCount;

    @Schema(name="名称")
    private String name;

    @Schema(name="排序")
    private Integer deptSort;

    /**
     * 1 启用 0 未启用
     * @see com.newland.mall.enumeration.BasicEnum
     */
    @Schema(name="状态")
    private Integer enabled;

    private List<SysDept> children;
}
