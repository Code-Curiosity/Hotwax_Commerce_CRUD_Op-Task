package com.example.ecomm.CRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    
    private Integer orderItemSeqId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private String status;
}
