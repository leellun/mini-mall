package com.newland.mall.system.model.vo;

import com.newland.mall.system.entity.SysDept;
import lombok.Data;

/**
 * 上级部门名称
 * Author: leell
 * Date: 2023/1/28 18:49:34
 */
@Data
public class SysDepartmentVo extends SysDept {
    private String parentName;
}
