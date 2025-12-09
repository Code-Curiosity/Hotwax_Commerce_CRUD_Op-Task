package com.example.ecomm.CRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

//Response for the 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Integer orderId;
    private LocalDate orderDate;
    private CustomerDto customer;
    private ContactMechDto shippingContact;
    private ContactMechDto billingContact;
    private List<OrderItemDto> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CustomerDto {
        private Integer customerId;
        private String firstName;
        private String lastName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContactMechDto {
        private Integer contactMechId;
        private String streetAddress;
        private String city;
        private String state;
        private String postalCode;
        private String phoneNumber;
        private String email;
    }
}
