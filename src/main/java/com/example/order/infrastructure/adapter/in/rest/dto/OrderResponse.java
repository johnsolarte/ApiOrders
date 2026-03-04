package com.example.order.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** DTO de respuesta del pedido completo */
public record OrderResponse(
    String id,
    String customerId,
    String status,
    List<OrderItemResponse> items,
    BigDecimal totalAmount,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
