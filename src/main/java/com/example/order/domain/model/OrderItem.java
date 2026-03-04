package com.example.order.domain.model;

import java.math.BigDecimal;

/**
 * Java 21 - Record: Inmutabilidad garantizada por el compilador.
 * Patron: Value Object (se define por sus valores, no por identidad).
 * SOLID - SRP: Solo representa un item del pedido.
 */
public record OrderItem(
    String productId,
    String productName,
    int quantity,
    BigDecimal unitPrice
) {
    // Validacion compacta del record (Java 21)
    public OrderItem {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be > 0");
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Unit price cannot be negative");
    }

    public BigDecimal totalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
