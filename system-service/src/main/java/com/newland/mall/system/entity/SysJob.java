package com.newland.mall.system.entity;

import com.newland.mall.model.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 岗位
 * sys_job
 * @author leell
 * @date 2023-02-21 15:38:48
 */
@Data
@Schema(name ="岗位")
public class SysJob extends BaseEntity {
    /**
     * ID
     */
    @Schema(name ="ID")
    private Long id;

    /**
     * 岗位名称
     */
    @Schema(name ="岗位名称")
    private String name;

    /**
     * 岗位状态
     */
    @Schema(name ="岗位状态")
    private Integer enabled;

    /**
     * 排序
     */
    @Schema(name ="排序")
    private Integer jobSort;

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