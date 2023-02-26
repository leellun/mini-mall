package com.newland.mall.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 重置密码
 * Author: leell
 * Date: 2022/12/7 00:59:05
 */
@Data
@Schema(name="修改密码", description="修改密码对象")
public class ResetPwdDto {
    @Schema(name= "密码")
    private String password;
    @Schema(name= "新密码")
    private String newPassword;
}
