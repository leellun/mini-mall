package com.newland.mall.recommend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BatchIdDTO implements Serializable {
    /**
     * id数组
     */
    Long[] ids;
}
