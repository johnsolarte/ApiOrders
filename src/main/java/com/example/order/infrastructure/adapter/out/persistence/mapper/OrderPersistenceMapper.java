package com.example.order.infrastructure.adapter.out.persistence.mapper;

import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderItem;
import com.example.order.domain.model.OrderStatus;
import com.example.order.infrastructure.adapter.out.persistence.entity.OrderEntity;
import com.example.order.infrastructure.adapter.out.persistence.entity.OrderItemEntity;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

/** Mapper dominio <-> JPA. SOLID SRP. Evita que JPA contamine el dominio. */
@ApplicationScoped
public class OrderPersistenceMapper {

    public OrderEntity toEntity(Order order) {
        OrderEntity e = new OrderEntity();
        e.id = order.getId();
        e.customerId = order.getCustomerId();
        e.status = order.getStatus().name();
        e.createdAt = order.getCreatedAt();
        e.updatedAt = order.getUpdatedAt();
        e.items = order.getItems().stream()
            .map(i -> new OrderItemEntity(i.productId(), i.productName(), i.quantity(), i.unitPrice()))
            .toList();
        return e;
    }

    public Order toDomain(OrderEntity e) {
        List<OrderItem> items = e.items.stream()
            .map(i -> new OrderItem(i.getProductId(), i.getProductName(), i.getQuantity(), i.getUnitPrice()))
            .toList();
        return Order.reconstitute(e.id, e.customerId, items,
            OrderStatus.valueOf(e.status), e.createdAt, e.updatedAt);
    }
}
