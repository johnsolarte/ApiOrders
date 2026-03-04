package com.example.order.infrastructure.adapter.in.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/** DTO para crear un pedido via REST */
public record CreateOrderRequest(
    @NotBlank(message = "customerId is required")
    String customerId,

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    List<OrderItemRequest> items
) {}
