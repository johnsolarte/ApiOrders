package com.example.order.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) - Adaptador de entrada REST.
 * Patron: DTO - separa la representacion HTTP del modelo de dominio.
 * SOLID SRP: solo transporta datos de la request.
 */
public record OrderItemRequest(
    @NotBlank(message = "productId is required")
    String productId,

    @NotBlank(message = "productName is required")
    String productName,

    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity,

    @NotNull @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal unitPrice
) {}
