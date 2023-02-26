package com.newland.mall.order.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 收货地址VO
 */
@Schema(name = "收获地址")
@Data
public class UserAddressVO {

    @Schema(name = "地址id")
    private Long addressId;

    @Schema(name = "用户id")
    private Long userId;

    @Schema(name = "收件人名称")
    private String userName;

    @Schema(name = "收件人联系方式")
    private String userPhone;

    @Schema(name = "是否默认地址 0-不是 1-是")
    private Byte defaultFlag;

    @Schema(name = "省")
    private String provinceName;

    @Schema(name = "市")
    private String cityName;

    @Schema(name = "区/县")
    private String regionName;

    @Schema(name = "详细地址")
    private String detailAddress;
}
