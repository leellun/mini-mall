package com.newland.mall.product.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateStockNumDTO {
    private List<StockNumDTO> stockNumDTOS;
}
