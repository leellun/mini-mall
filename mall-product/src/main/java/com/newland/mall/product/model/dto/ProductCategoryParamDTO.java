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

@Schema(name = "分类信息")
@Data
public class ProductCategoryParamDTO {
    @Schema(name ="待修改分类id")
    @NotNull(message = "分类id不能为空",groups = Update.class)
    @Min(value = 1, message = "分类id不能为空",groups = Update.class)
    private Long categoryId;
    @Schema(name ="分类层级")
    private Integer categoryLevel;

    @Schema(name ="父类id")
    @NotNull(message = "parentId不能为空",groups = {Insert.class, Update.class})
    @Min(value = 0, message = "parentId最低为0",groups = {Insert.class, Update.class})
    private Long parentId;

    @Schema(name ="分类名称")
    @NotEmpty(message = "categoryName不能为空",groups = {Insert.class, Update.class})
    @Length(max = 16,message = "分类名称过长",groups = {Insert.class, Update.class})
    private String categoryName;

    @Schema(name ="排序值")
    @Min(value = 1, message = "categoryRank最低为1",groups = {Insert.class, Update.class})
    @Max(value = 200, message = "categoryRank最高为200",groups = {Insert.class, Update.class})
    @NotNull(message = "categoryRank不能为空",groups = {Insert.class, Update.class})
    private Integer categoryRank;
}