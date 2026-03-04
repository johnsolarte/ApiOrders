package com.example.order.application.usecase;

import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderItem;
import com.example.order.domain.port.in.CreateOrderUseCase;
import com.example.order.domain.port.out.OrderEventPublisher;
import com.example.order.domain.port.out.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

/** CAPA APLICACION - Orquesta el caso de uso.
 * SOLID DIP: Depende de interfaces (puertos), no de implementaciones.
 * Hexagonal: implementa puerto entrada, usa puertos salida.
 * Quarkus CDI: @ApplicationScoped = singleton. */
@ApplicationScoped
public class CreateOrderService implements CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;
    @Inject
    public CreateOrderService(OrderRepository r, OrderEventPublisher e) {
        this.orderRepository = r; this.eventPublisher = e;
    }
    @Override
    @Transactional
    public Order execute(CreateOrderCommand command) {
        List<OrderItem> items = command.items().stream()
            .map(i -> new OrderItem(i.productId(), i.productName(), i.quantity(), i.unitPrice()))
            .toList();
        Order order = Order.create(command.customerId(), items);
        Order saved = orderRepository.save(order);
        eventPublisher.publishOrderCreated(saved);
        return saved;
    }
}
