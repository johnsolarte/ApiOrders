package com.example.order.domain.port.in;

import com.example.order.domain.model.Order;
import java.math.BigDecimal;
import java.util.List;

/**
 * ARQUITECTURA HEXAGONAL - Puerto de ENTRADA.
 * SOLID - ISP: cada interfaz define UN caso de uso.
 * SOLID - DIP: dependemos de abstracciones, no de implementaciones.
 */
public interface CreateOrderUseCase {
    record OrderItemCommand(String productId, String productName, int quantity, BigDecimal unitPrice) {}
    record CreateOrderCommand(String customerId, List<OrderItemCommand> items) {}
    Order execute(CreateOrderCommand command);
}
