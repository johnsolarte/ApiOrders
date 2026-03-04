package com.example.order.infrastructure.adapter.out.kafka;

import com.example.order.domain.model.Order;
import com.example.order.domain.port.out.OrderEventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * ADAPTADOR SECUNDARIO (de salida) - Apache Kafka.
 * ARQUITECTURA HEXAGONAL: implementa el puerto de salida OrderEventPublisher.
 * El dominio no sabe que existe Kafka. Solo llama al puerto.
 *
 * Quarkus SmallRye Reactive Messaging:
 *  - @Channel: inyecta el canal de Kafka configurado en application.properties.
 *  - Emitter: permite enviar mensajes de forma imperativa.
 *
 * PATRON: Adapter (convierte la interfaz del puerto a la API de Kafka).
 */
@ApplicationScoped
public class OrderEventPublisherKafkaAdapter implements OrderEventPublisher {

    @Inject
    @Channel("order-events-out")
    Emitter<String> orderEventsEmitter;

    private final ObjectMapper objectMapper;

    public OrderEventPublisherKafkaAdapter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void publishOrderCreated(Order order) {
        publishEvent("ORDER_CREATED", order, null);
    }

    @Override
    public void publishOrderStatusChanged(Order order, String previousStatus) {
        publishEvent("ORDER_STATUS_CHANGED", order, previousStatus);
    }

    private void publishEvent(String eventType, Order order, String previousStatus) {
        try {
            Map<String, Object> event = Map.of(
                "eventType", eventType,
                "orderId", order.getId(),
                "customerId", order.getCustomerId(),
                "status", order.getStatus().name(),
                "previousStatus", previousStatus != null ? previousStatus : "",
                "totalAmount", order.totalAmount(),
                "timestamp", LocalDateTime.now().toString()
            );
            String json = objectMapper.writeValueAsString(event);
            orderEventsEmitter.send(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing order event", e);
        }
    }
}
