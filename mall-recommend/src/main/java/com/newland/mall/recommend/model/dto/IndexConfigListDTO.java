package com.newland.mall.recommend.model.dto;

import com.newland.mybatis.page.PageEntity;
import lombok.Data;

/**
 * Author: leell
 * Date: 2023/2/25 12:25:10
 */
@Data
public class IndexConfigListDTO extends PageEntity {
    private Integer configType;
}
