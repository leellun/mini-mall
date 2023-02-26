package com.newland.mall.order.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 添加收货地址
 */
@Schema(name = "添加收货地址")
@Data
public class UserAddressParamDTO {

    @Schema(name ="地址id")
    private Long addressId;

    @Schema(name ="用户id")
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
