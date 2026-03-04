package com.example.order.domain.port.out;

import com.example.order.domain.model.Order;
import java.util.List;
import java.util.Optional;

/**
 * ARQUITECTURA HEXAGONAL - Puerto de SALIDA.
 * El dominio define el contrato. La infra lo implementa.
 * SOLID - DIP: El dominio no conoce PostgreSQL/MongoDB.
 */
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(String id);
    List<Order> findByCustomerId(String customerId);
    List<Order> findAll();
    void deleteById(String id);
}
