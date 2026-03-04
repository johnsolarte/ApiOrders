package com.example.order.infrastructure.adapter.out.kafka;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

/** Consumer Kafka - escucha eventos de otros servicios. */
@ApplicationScoped
public class OrderEventConsumer {
    private static final Logger LOG = Logger.getLogger(OrderEventConsumer.class);

    @Incoming("order-notifications-in")
    public void processNotification(String message) {
        LOG.infof("Received notification from Kafka: %s", message);
    }
}
