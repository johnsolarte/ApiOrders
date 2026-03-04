package com.example.order.infrastructure.adapter.out.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA - solo existe en la capa de infraestructura.
 * ARQUITECTURA HEXAGONAL: el dominio NO conoce esta clase.
 * Extiende PanacheEntityBase de Quarkus para operaciones de BD.
 */
@Entity
@Table(name = "orders")
public class OrderEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id", length = 36)
    public String id;

    @Column(name = "customer_id", nullable = false)
    public String customerId;

    @Column(name = "status", nullable = false)
    public String status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    public List<OrderItemEntity> items = new ArrayList<>();

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    public OrderEntity() {}
}
