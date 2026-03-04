package com.example.order.domain.model;

/** Patron Value Object - estados posibles de un pedido (SOLID - SRP) */
public enum OrderStatus {
    PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}
