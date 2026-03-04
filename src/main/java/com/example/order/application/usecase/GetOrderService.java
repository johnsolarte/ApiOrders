package com.example.order.application.usecase;
import com.example.order.domain.exception.OrderNotFoundException;
import com.example.order.domain.model.Order;
import com.example.order.domain.port.in.GetOrderUseCase;
import com.example.order.domain.port.out.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
/** SOLID SRP: Solo gestiona consultas de pedidos */
@ApplicationScoped
public class GetOrderService implements GetOrderUseCase {
    private final OrderRepository repo;
    @Inject public GetOrderService(OrderRepository repo) { this.repo = repo; }
    @Override public Order findById(String id) {
        return repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
    @Override public List<Order> findByCustomer(String cid) { return repo.findByCustomerId(cid); }
    @Override public List<Order> findAll() { return repo.findAll(); }
}
