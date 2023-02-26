package com.newland.mall.member.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户修改
 */
@Schema(name = "用户修改")
@Data
public class UserUpdateDTO implements Serializable {

    @Schema(name = "用户昵称")
    private String nickName;

    @Schema(name = "用户密码(需要MD5加密)")
    private String passwordMd5;

    @Schema(name = "个性签名")
    private String introduceSign;

}
