package com.newland.mall.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 收货地址表
 * oms_user_address
 * @author leell
 * @date 2023-02-25 14:07:25
 */
@Data
@Schema(name ="收货地址表")
public class UserAddress implements Serializable {
    /**
     */
    private Long addressId;

    /**
     * 用户主键id
     */
    @Schema(name ="用户主键id")
    private Long userId;

    /**
     * 收货人姓名
     */
    @Schema(name ="收货人姓名")
    private String userName;

    /**
     * 收货人手机号
     */
    @Schema(name ="收货人手机号")
    private String userPhone;

    /**
     * 是否为默认 0-非默认 1-是默认
     */
    @Schema(name ="是否为默认 0-非默认 1-是默认")
    private Integer defaultFlag;

    /**
     * 省
     */
    @Schema(name ="省")
    private String provinceName;

    /**
     * 城
     */
    @Schema(name ="城")
    private String cityName;

    /**
     * 区
     */
    @Schema(name ="区")
    private String regionName;

    /**
     * 收件详细地址(街道/楼宇/单元)
     */
    @Schema(name ="收件详细地址(街道/楼宇/单元)")
    private String detailAddress;

    /**
     * 删除标识字段(0-未删除 1-已删除)
     */
    @Schema(name ="删除标识字段(0-未删除 1-已删除)")
    private Integer isDeleted;

    /**
     * 添加时间
     */
    @Schema(name ="添加时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Schema(name ="修改时间")
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}