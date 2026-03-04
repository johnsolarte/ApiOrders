package com.example.order.domain.port.out;

import com.example.order.domain.model.Order;

/**
 * ARQUITECTURA HEXAGONAL - Puerto de SALIDA para eventos.
 * El dominio no sabe que existe Kafka. Solo pide "publicar evento".
 * SOLID - DIP: no dependemos de KafkaProducer directamente.
 */
public interface OrderEventPublisher {
    void publishOrderCreated(Order order);
    void publishOrderStatusChanged(Order order, String previousStatus);
}
