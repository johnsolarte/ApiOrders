package com.example.order.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

/** DTO de respuesta para un item del pedido */
public record OrderItemResponse(
    String productId,
    String productName,
    int quantity,
    BigDecimal unitPrice,
    BigDecimal totalPrice
) {}
