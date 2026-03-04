package com.example.order.infrastructure.adapter.in.rest.mapper;

import com.example.order.domain.model.Order;
import com.example.order.domain.port.in.CreateOrderUseCase;
import com.example.order.infrastructure.adapter.in.rest.dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

/** Patron Mapper - convierte DTOs REST a/de objetos de dominio. SOLID SRP. */
@ApplicationScoped
public class OrderRestMapper {

    public CreateOrderUseCase.CreateOrderCommand toCommand(CreateOrderRequest req) {
        List<CreateOrderUseCase.OrderItemCommand> items = req.items().stream()
            .map(i -> new CreateOrderUseCase.OrderItemCommand(
                i.productId(), i.productName(), i.quantity(), i.unitPrice()))
            .toList();
        return new CreateOrderUseCase.CreateOrderCommand(req.customerId(), items);
    }

    public OrderResponse toResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
            .map(i -> new OrderItemResponse(
                i.productId(), i.productName(), i.quantity(), i.unitPrice(), i.totalPrice()))
            .toList();
        return new OrderResponse(order.getId(), order.getCustomerId(), order.getStatus().name(),
            itemResponses, order.totalAmount(), order.getCreatedAt(), order.getUpdatedAt());
    }

    public List<OrderResponse> toResponseList(List<Order> orders) {
        return orders.stream().map(this::toResponse).toList();
    }
}
