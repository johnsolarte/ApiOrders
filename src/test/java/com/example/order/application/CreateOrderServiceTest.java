package com.example.order.application;

import com.example.order.application.usecase.CreateOrderService;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderStatus;
import com.example.order.domain.port.in.CreateOrderUseCase;
import com.example.order.domain.port.out.OrderEventPublisher;
import com.example.order.domain.port.out.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TEST UNITARIO - No necesita BD ni Kafka.
 * Hexagonal: testamos el nucleo sin infraestructura.
 * Mockito simula los puertos de salida.
 */
@ExtendWith(MockitoExtension.class)
class CreateOrderServiceTest {

    @Mock OrderRepository orderRepository;
    @Mock OrderEventPublisher eventPublisher;
    CreateOrderService service;

    @BeforeEach
    void setUp() {
        service = new CreateOrderService(orderRepository, eventPublisher);
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        var command = new CreateOrderUseCase.CreateOrderCommand("customer-001",
            List.of(new CreateOrderUseCase.OrderItemCommand("p1","Laptop",1,new BigDecimal("1500.00"))));

        Order result = service.execute(command);

        assertNotNull(result.getId());
        assertEquals("customer-001", result.getCustomerId());
        assertEquals(OrderStatus.PENDING, result.getStatus());
        assertEquals(new BigDecimal("1500.00"), result.totalAmount());
        verify(orderRepository).save(any());
        verify(eventPublisher).publishOrderCreated(any());
    }


    @Test
    void shouldCalculateTotalAmountCorrectly() {
        var command = new CreateOrderUseCase.CreateOrderCommand("cust-1", List.of(
            new CreateOrderUseCase.OrderItemCommand("p1","A",2,new BigDecimal("100.00")),
            new CreateOrderUseCase.OrderItemCommand("p2","B",3,new BigDecimal("50.00"))
        ));
        Order result = service.execute(command);
        assertEquals(new BigDecimal("350.00"), result.totalAmount());
    }
}
