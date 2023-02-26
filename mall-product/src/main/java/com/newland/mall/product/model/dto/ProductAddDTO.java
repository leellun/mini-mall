package com.newland.mall.product.model.dto;

import com.newland.mall.validator.Insert;
import com.newland.mall.validator.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Schema(name = "添加商品")
@Data
public class ProductAddDTO {
    @Schema(name = "待修改商品id")
    @NotNull(message = "商品id不能为空",groups = Update.class)
    @Min(value = 1, message = "商品id不能为空")
    private Long goodsId;
    @Schema(name = "商品名称")
    @NotEmpty(message = "商品名称不能为空",groups = {Insert.class,Update.class})
    @Length(max = 128, message = "商品名称内容过长",groups = {Insert.class,Update.class})
    private String goodsName;

    @Schema(name = "商品简介")
    @NotEmpty(message = "商品简介不能为空",groups = {Insert.class,Update.class})
    @Length(max = 200, message = "商品简介内容过长",groups = {Insert.class,Update.class})
    private String goodsIntro;

    @Schema(name = "分类id")
    @NotNull(message = "分类id不能为空",groups = {Insert.class,Update.class})
    @Min(value = 1, message = "分类id最低为1",groups = {Insert.class,Update.class})
    private Long goodsCategoryId;

    @Schema(name = "商品主图")
    @NotEmpty(message = "商品主图不能为空",groups = {Insert.class,Update.class})
    private String goodsCoverImg;

    @Schema(name = "originalPrice")
    @NotNull(message = "originalPrice不能为空",groups = {Insert.class,Update.class})
    @Min(value = 1, message = "originalPrice最低为1",groups = {Insert.class,Update.class})
    @Max(value = 1000000, message = "originalPrice最高为1000000",groups = {Insert.class,Update.class})
    private Integer originalPrice;

    @Schema(name = "sellingPrice")
    @NotNull(message = "sellingPrice不能为空",groups = {Insert.class,Update.class})
    @Min(value = 1, message = "sellingPrice最低为1",groups = {Insert.class,Update.class})
    @Max(value = 1000000, message = "sellingPrice最高为1000000",groups = {Insert.class,Update.class})
    private Integer sellingPrice;

    @Schema(name = "库存")
    @NotNull(message = "库存不能为空",groups = {Insert.class,Update.class})
    @Min(value = 1, message = "库存最低为1",groups = {Insert.class,Update.class})
    @Max(value = 100000, message = "库存最高为100000",groups = {Insert.class,Update.class})
    private Integer stockNum;

    @Schema(name = "商品标签")
    @NotEmpty(message = "商品标签不能为空",groups = {Insert.class,Update.class})
    @Length(max = 16, message = "商品标签内容过长",groups = {Insert.class,Update.class})
    private String tag;

    private Integer goodsSellStatus;

    @Schema(name = "商品详情")
    @NotEmpty(message = "商品详情不能为空",groups = {Insert.class,Update.class})
    private String goodsDetailContent;
}