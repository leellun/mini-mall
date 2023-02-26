package com.newland.mall.system.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer;
import com.newland.mall.system.entity.SysUser;
import com.newland.mall.validator.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 用户添加、修改
 * Author: leell
 * Date: 2022/12/7 14:16:15
 */
@Data
@Schema(name = "用户列表返回对象")
public class SysUserDto extends SysUser {
    @Schema(name = "ID")
    @NotNull(message = "id不能为空",groups = {Update.class})
    private Long id;

    @Schema(name = "角色id")
    @JsonSerialize(using= IndexedStringListSerializer.class)
    private List<Long> roleIds;
}
