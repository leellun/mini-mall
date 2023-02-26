package com.newland.mall.member.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(name = "用户列表")
@Data
public class UserVO implements Serializable {

    @Schema(name = "用户昵称")
    private String nickName;

    @Schema(name = "用户登录名")
    private String loginName;

    @Schema(name = "个性签名")
    private String introduceSign;
}
