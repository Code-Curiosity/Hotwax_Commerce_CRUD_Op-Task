package com.example.ecomm.CRUD.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    @NotNull
    private LocalDate orderDate;

    @NotNull
    private Integer customerId;

    @NotNull
    private Integer shippingContactMechId;

    @NotNull
    private Integer billingContactMechId;

    @NotNull
    @Size(min = 1)
    private List<OrderItemRequest> orderItems;
}
