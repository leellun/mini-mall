package com.newland.mall.recommend.model.dto;

import com.newland.mall.validator.Insert;
import com.newland.mall.validator.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Schema(name = "添加更新轮播")
@Data
public class CarouselParamDTO {

    @Schema(name ="待修改轮播图id")
    @NotNull(message = "轮播图id不能为空",groups = Update.class)
    @Min(value = 1,groups = Update.class)
    private Long carouselId;
    @Schema(name = "轮播图URL地址")
    @NotEmpty(message = "轮播图URL不能为空",groups = {Insert.class, Update.class})
    private String carouselUrl;

    @Schema(name = "轮播图跳转地址")
    @NotEmpty(message = "轮播图跳转地址不能为空",groups = {Insert.class, Update.class})
    private String redirectUrl;

    @Schema(name = "排序值")
    @Min(value = 1, message = "carouselRank最低为1",groups = {Insert.class, Update.class})
    @Max(value = 200, message = "carouselRank最高为200",groups = {Insert.class, Update.class})
    @NotNull(message = "carouselRank不能为空",groups = {Insert.class, Update.class})
    private Integer carouselRank;
}
