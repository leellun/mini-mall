package com.newland.mall.member.model.dto;

import com.newland.mall.member.common.MemberServiceError;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册
 */
@Schema(name = "用户注册")
@Data
public class UserRegisterDTO implements Serializable {

    @Schema(name = "登录名")
    @NotEmpty(message = "登录名不能为空")
    @Pattern(regexp = "\\d{11}",message = MemberServiceError.LOGIN_NAME_IS_NOT_PHONE)
    private String loginName;

    @Schema(name = "用户密码")
    @NotEmpty(message = "密码不能为空")
    private String password;
}
