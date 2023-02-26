package com.newland.mall.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 当前电商登录用户
 * Author: leell
 * Date: 2023/2/23 22:17:28
 */
@Schema(name = "电商用户")
@Data
public class MallUser {
    /**
     * 用户主键id
     */
    @Schema(name ="用户主键id")
    private Long userId;


    /**
     * 登陆名称(默认为手机号)
     */
    @Schema(name ="登陆名称(默认为手机号)")
    private String loginName;
}
