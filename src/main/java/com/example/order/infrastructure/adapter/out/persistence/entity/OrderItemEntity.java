package com.example.order.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/** Entidad JPA para items. Separada del Value Object del dominio. */
@Embeddable
public class OrderItemEntity {
    private String productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;

    public OrderItemEntity() {}
    public OrderItemEntity(String productId, String productName, int quantity, BigDecimal unitPrice) {
        this.productId = productId; this.productName = productName;
        this.quantity = quantity; this.unitPrice = unitPrice;
    }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setProductId(String v) { this.productId = v; }
    public void setProductName(String v) { this.productName = v; }
    public void setQuantity(int v) { this.quantity = v; }
    public void setUnitPrice(BigDecimal v) { this.unitPrice = v; }
}
