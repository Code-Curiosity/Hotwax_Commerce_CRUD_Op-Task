package com.example.ecomm.CRUD.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequest {

    @NotNull
    private Integer productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotBlank
    private String status;
}
