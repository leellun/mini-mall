package com.newland.mall.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 登录dto
 * Author: leell
 * Date: 2022/12/6 15:09:37
 */
@Data
@NoArgsConstructor
@Schema(name = "LoginDTO", description = "账户注册信息")
public class LoginDto {

    @NotEmpty(message = "请输入用户名")
    @Schema(name = "用户名")
    private String username;
    @NotEmpty(message = "请输入密码")
    @Schema(name = "密码")
    private String password;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
