package com.example.order.application.usecase;

import com.example.order.domain.exception.OrderNotFoundException;
import com.example.order.domain.model.Order;
import com.example.order.domain.port.in.UpdateOrderStatusUseCase;
import com.example.order.domain.port.out.OrderEventPublisher;
import com.example.order.domain.port.out.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UpdateOrderStatusService implements UpdateOrderStatusUseCase {
    private final OrderRepository repo;
    private final OrderEventPublisher pub;

    @Inject
    public UpdateOrderStatusService(OrderRepository r, OrderEventPublisher p) { repo=r; pub=p; }

    @Override @Transactional
    public Order confirm(String id) {
        Order o = findOrThrow(id);
        String prev = o.getStatus().name();
        o.confirm();
        Order saved = repo.save(o);
        try {
            pub.publishOrderStatusChanged(saved, prev);
        } catch (Exception e) {
            System.out.println("Error publishing event: " + e.getMessage());
        }
        return saved;
    }

    @Override @Transactional
    public Order cancel(String id) {
        Order o = findOrThrow(id);
        String prev = o.getStatus().name();
        o.cancel();
        Order saved = repo.save(o);
        try {
            pub.publishOrderStatusChanged(saved, prev);
        } catch (Exception e) {
            System.out.println("Error publishing event: " + e.getMessage());
        }
        return saved;
    }

    @Override @Transactional
    public Order startProcessing(String id) {
        Order o = findOrThrow(id);
        String prev = o.getStatus().name();
        o.startProcessing();
        Order saved = repo.save(o);
        try {
            pub.publishOrderStatusChanged(saved, prev);
        } catch (Exception e) {
            System.out.println("Error publishing event: " + e.getMessage());
        }
        return saved;
    }

    private Order findOrThrow(String id) {
        return repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}
