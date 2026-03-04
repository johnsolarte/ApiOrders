package com.example.order.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * DOMINIO PURO - Sin dependencias de frameworks.
 * Patrones: Entity, Aggregate Root, Factory Method.
 * SOLID: SRP, OCP.
 * Java 21: Pattern matching en switch.
 */
public class Order {
    private final String id;
    private final String customerId;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Order(String id, String customerId, List<OrderItem> items,
                  OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    /** PATRON Factory Method */
    public static Order create(String customerId, List<OrderItem> items) {
        if (customerId == null || customerId.isBlank())
            throw new IllegalArgumentException("CustomerId cannot be blank");
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("Order must have at least one item");
        return new Order(UUID.randomUUID().toString(), customerId, items,
                         OrderStatus.PENDING, LocalDateTime.now());
    }

    /** Reconstruir desde persistencia */
    public static Order reconstitute(String id, String customerId, List<OrderItem> items,
                                      OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        Order o = new Order(id, customerId, items, status, createdAt);
        o.updatedAt = updatedAt;
        return o;
    }

    public void confirm() {
        if (this.status != OrderStatus.PENDING)
            throw new IllegalStateException("Only PENDING orders can be confirmed");
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        switch (this.status) {
            case SHIPPED, DELIVERED ->
                throw new IllegalStateException("Cannot cancel order in status: " + this.status);
            default -> {
                this.status = OrderStatus.CANCELLED;
                this.updatedAt = LocalDateTime.now();
            }
        }
    }

    public void startProcessing() {
        if (this.status != OrderStatus.CONFIRMED)
            throw new IllegalStateException("Only CONFIRMED orders can be processed");
        this.status = OrderStatus.PROCESSING;
        this.updatedAt = LocalDateTime.now();
    }

    public BigDecimal totalAmount() {
        return items.stream()
            .map(OrderItem::totalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
