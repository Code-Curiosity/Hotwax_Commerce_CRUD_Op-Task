package com.example.ecomm.CRUD.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderRequest {

    // Optional: only provide fields you want to update
    private LocalDate orderDate;
    private Integer shippingContactMechId;
    private Integer billingContactMechId;
}

