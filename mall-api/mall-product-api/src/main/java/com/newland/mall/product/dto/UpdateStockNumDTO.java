package com.newland.mall.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateStockNumDTO {
    private List<StockNumDTO> stockNumDTOS;
}
