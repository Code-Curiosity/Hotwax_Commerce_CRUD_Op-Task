package com.example.ecomm.CRUD.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Hibernate;

import com.example.ecomm.CRUD.dto.*;
import com.example.ecomm.CRUD.entity.*;
import com.example.ecomm.CRUD.exception.ResourceNotFoundException;
import com.example.ecomm.CRUD.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl{

    private final CustomerRepository customerRepo;
    private final ContactMechRepository contactMechRepo;
    private final ProductRepository productRepo;
    private final OrderHeaderRepository orderHeaderRepo;
    private final OrderItemRepository orderItemRepo;

    // Create
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        OrderHeader header = buildHeaderFromReq(request);
        OrderHeader saved = orderHeaderRepo.save(header);
        return toDto(saved);
    }

    // Read
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Integer orderId) {
        OrderHeader header = orderHeaderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found id=" + orderId));
        Hibernate.initialize(header.getOrderItems()); 
        return toDto(header);
    }

    //Header Update
    @Transactional
    public OrderResponse updateOrder(Integer orderId, UpdateOrderRequest request) {
        OrderHeader header = orderHeaderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found id=" + orderId));

        if (request.getOrderDate() != null) header.setOrderDate(request.getOrderDate());
        if (request.getShippingContactMechId() != null) {
            header.setShippingContact(loadContact(request.getShippingContactMechId()));
        }
        if (request.getBillingContactMechId() != null) {
            header.setBillingContact(loadContact(request.getBillingContactMechId()));
        }

        return toDto(header); 
    }

    //Delete Order
    @Transactional
    public void deleteOrder(Integer orderId) {
        OrderHeader header = orderHeaderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found id=" + orderId));
        orderHeaderRepo.delete(header); // cascade + orphanRemoval will remove items
    }

    //Add Items
    @Transactional
    public OrderItemDto addOrderItem(Integer orderId, OrderItemRequest request) {
        OrderHeader header = orderHeaderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found id=" + orderId));

        Product p = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found id=" + request.getProductId()));

        OrderItem item = new OrderItem();
        item.setProduct(p);
        item.setQuantity(request.getQuantity());
        item.setStatus(request.getStatus());
        item.setOrderHeader(header);

        orderItemRepo.save(item);
        header.getOrderItems().add(item);
        return itemToDto(item);
    }

    // Update Items
    @Transactional
    public OrderItemDto updateOrderItem(Integer orderId, Integer orderItemSeqId, OrderItemRequest request) {
        OrderItem existing = orderItemRepo.findById(orderItemSeqId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found id=" + orderItemSeqId));

        if (existing.getOrderHeader() == null || !existing.getOrderHeader().getOrderId().equals(orderId)) {
            throw new ResourceNotFoundException("Order item does not belong to order id=" + orderId);
        }

        if (request.getProductId() != null) {
            Product p = productRepo.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found id=" + request.getProductId()));
            existing.setProduct(p);
        }
        if (request.getQuantity() != null) existing.setQuantity(request.getQuantity());
        if (request.getStatus() != null) existing.setStatus(request.getStatus());

        OrderItem saved = orderItemRepo.save(existing);
        return itemToDto(saved);
    }

    // Delete Item
    @Transactional
    public void deleteOrderItem(Integer orderId, Integer orderItemSeqId) {
        OrderItem existing = orderItemRepo.findById(orderItemSeqId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found id=" + orderItemSeqId));

        if (existing.getOrderHeader() == null || !existing.getOrderHeader().getOrderId().equals(orderId)) {
            throw new ResourceNotFoundException("Order item does not belong to order id=" + orderId);
        }

        orderItemRepo.delete(existing);
    }

    // Helper and Mapper

    private OrderHeader buildHeaderFromReq(CreateOrderRequest req) {
        OrderHeader header = new OrderHeader();
        header.setOrderDate(req.getOrderDate());
        header.setCustomer(loadCustomer(req.getCustomerId()));
        header.setShippingContact(loadContact(req.getShippingContactMechId()));
        header.setBillingContact(loadContact(req.getBillingContactMechId()));
        List<OrderItem> items = buildItems(req.getOrderItems(), header);
        header.setOrderItems(items);
        return header;
    }

    private Customer loadCustomer(Integer customerId) {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found id=" + customerId));
    }

    private ContactMech loadContact(Integer contactMechId) {
        return contactMechRepo.findById(contactMechId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found id=" + contactMechId));
    }

    private List<OrderItem> buildItems(List<OrderItemRequest> itemReqs, OrderHeader header) {
        return itemReqs.stream().map(itReq -> {
            Product product = productRepo.findById(itReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found id=" + itReq.getProductId()));
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itReq.getQuantity());
            item.setStatus(itReq.getStatus());
            item.setOrderHeader(header);
            return item;
        }).collect(Collectors.toList());
    }

    private OrderResponse toDto(OrderHeader h) {
        return OrderResponse.builder()
                .orderId(h.getOrderId())
                .orderDate(h.getOrderDate())
                .customer(buildCustomerBrief(h.getCustomer()))
                .shippingContact(buildContactBrief(h.getShippingContact()))
                .billingContact(buildContactBrief(h.getBillingContact()))
                .items(h.getOrderItems().stream().map(this::itemToDto).collect(Collectors.toList()))
                .build();
    }

    private OrderItemDto itemToDto(OrderItem i) {
        return OrderItemDto.builder()
                .orderItemSeqId(i.getOrderItemSeqId())
                .productId(i.getProduct() != null ? i.getProduct().getProductId() : null)
                .productName(i.getProduct() != null ? i.getProduct().getName() : null)
                .quantity(i.getQuantity())
                .status(i.getStatus())
                .build();
    }

    private OrderResponse.CustomerDto buildCustomerBrief(Customer c) {
        if (c == null) return null;
        return OrderResponse.CustomerDto.builder()
                .customerId(c.getCustomerId())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .build();
    }

    private OrderResponse.ContactMechDto buildContactBrief(ContactMech cm) {
        if (cm == null) return null;
        return OrderResponse.ContactMechDto.builder()
                .contactMechId(cm.getContactMechId())
                .streetAddress(cm.getStreetAddress())
                .city(cm.getCity())
                .state(cm.getState())
                .postalCode(cm.getPostalCode())
                .phoneNumber(cm.getPhoneNumber())
                .email(cm.getEmail())
                .build();
    }
}