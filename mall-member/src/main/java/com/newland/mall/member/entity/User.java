package com.newland.mall.member.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ums_user
 * @author leell
 * @date 2023-02-23 19:43:27
 */
@Data
public class User implements Serializable {
    /**
     * 用户主键id
     */
    @Schema(name ="用户主键id")
    private Long userId;

    /**
     * 用户昵称
     */
    @Schema(name ="用户昵称")
    private String nickName;

    /**
     * 登陆名称(默认为手机号)
     */
    @Schema(name ="登陆名称(默认为手机号)")
    private String loginName;

    /**
     * MD5加密后的密码
     */
    @Schema(name ="MD5加密后的密码")
    private String passwordMd5;

    /**
     * 个性签名
     */
    @Schema(name ="个性签名")
    private String introduceSign;

    /**
     * 注销标识字段(0-正常 1-已注销)
     */
    @Schema(name ="注销标识字段(0-正常 1-已注销)")
    private Integer isDeleted;

    /**
     * 锁定标识字段(0-未锁定 1-已锁定)
     */
    @Schema(name ="锁定标识字段(0-未锁定 1-已锁定)")
    private Integer lockedFlag;

    /**
     * 注册时间
     */
    @Schema(name ="注册时间")
    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}