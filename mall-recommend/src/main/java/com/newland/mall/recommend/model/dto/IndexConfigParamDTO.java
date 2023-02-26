package com.newland.mall.recommend.model.dto;

import com.newland.mall.validator.Insert;
import com.newland.mall.validator.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(name = "首页配置")
@Data
public class IndexConfigParamDTO {

    @Schema(name = "待修改配置id")
    @NotNull(message = "configId不能为空", groups = Update.class)
    @Min(value = 1, message = "configId不能为空", groups = Update.class)
    private Long configId;

    @Schema(name = "配置的名称")
    @NotEmpty(message = "configName不能为空", groups = {Insert.class, Update.class})
    private String configName;

    @Schema(name = "配置类别")
    @NotNull(message = "configType不能为空", groups = {Insert.class, Update.class})
    @Min(value = 1, message = "configType最小为1", groups = {Insert.class, Update.class})
    @Max(value = 5, message = "configType最大为5", groups = {Insert.class, Update.class})
    private Integer configType;

    @Schema(name = "商品id")
    @NotNull(message = "商品id不能为空", groups = {Insert.class, Update.class})
    @Min(value = 1, message = "商品id不能为空", groups = {Insert.class, Update.class})
    private Long goodsId;

    @Schema(name = "排序值")
    @Min(value = 1, message = "configRank最低为1", groups = {Insert.class, Update.class})
    @Max(value = 200, message = "configRank最高为200", groups = {Insert.class, Update.class})
    @NotNull(message = "configRank不能为空", groups = {Insert.class, Update.class})
    private Integer configRank;
    @Schema(name = "跳转链接")
    private String redirectUrl;
}