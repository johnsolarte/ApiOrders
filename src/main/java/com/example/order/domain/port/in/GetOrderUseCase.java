package com.example.order.domain.port.in;

import com.example.order.domain.model.Order;
import java.util.List;

/** Puerto de entrada: consultar pedidos */
public interface GetOrderUseCase {
    Order findById(String orderId);
    List<Order> findByCustomer(String customerId);
    List<Order> findAll();
}
