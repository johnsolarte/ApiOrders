package com.example.order.domain.port.in;

import com.example.order.domain.model.Order;

/** Puerto de entrada: cambiar estado del pedido */
public interface UpdateOrderStatusUseCase {
    Order confirm(String orderId);
    Order cancel(String orderId);
    Order startProcessing(String orderId);
}
