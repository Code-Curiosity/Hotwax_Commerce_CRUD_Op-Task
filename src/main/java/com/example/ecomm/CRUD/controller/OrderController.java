package com.example.ecomm.CRUD.controller;

import com.example.ecomm.CRUD.dto.CreateOrderRequest;
import com.example.ecomm.CRUD.dto.OrderItemDto;
import com.example.ecomm.CRUD.dto.OrderItemRequest;
import com.example.ecomm.CRUD.dto.OrderResponse;
import com.example.ecomm.CRUD.dto.UpdateOrderRequest;
import com.example.ecomm.CRUD.service.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    // Create order
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest req) {
        OrderResponse resp = orderService.createOrder(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // Retrieve order
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    // Update order header
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Integer orderId,
                                                     @Valid @RequestBody UpdateOrderRequest req) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, req));
    }

    // Delete order
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
    }

    // Add order item
    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItemDto> addOrderItem(@PathVariable Integer orderId,
                                                     @Valid @RequestBody OrderItemRequest req) {
        OrderItemDto dto = orderService.addOrderItem(orderId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Update order item
    @PutMapping("/{orderId}/items/{orderItemSeqId}")
    public ResponseEntity<OrderItemDto> updateOrderItem(@PathVariable Integer orderId,
                                                        @PathVariable Integer orderItemSeqId,
                                                        @Valid @RequestBody OrderItemRequest req) {
        return ResponseEntity.ok(orderService.updateOrderItem(orderId, orderItemSeqId, req));
    }

    // Delete order item
    @DeleteMapping("/{orderId}/items/{orderItemSeqId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItem(@PathVariable Integer orderId, @PathVariable Integer orderItemSeqId) {
        orderService.deleteOrderItem(orderId, orderItemSeqId);
    }
}
