package com.example.order.infrastructure.adapter.out.persistence.repository;

import com.example.order.domain.model.Order;
import com.example.order.domain.port.out.OrderRepository;
import com.example.order.infrastructure.adapter.out.persistence.entity.OrderEntity;
import com.example.order.infrastructure.adapter.out.persistence.mapper.OrderPersistenceMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * ADAPTADOR SECUNDARIO (de salida) - PostgreSQL via Panache.
 * ARQUITECTURA HEXAGONAL: implementa el puerto de salida OrderRepository.
 * El dominio define el contrato, esta clase lo cumple con JPA/Panache.
 * PATRON Adapter: convierte la API de Panache al contrato del puerto.
 */
@ApplicationScoped
public class OrderRepositoryAdapter implements OrderRepository {

    @Inject
    OrderPersistenceMapper mapper;

    @Override
    public Order save(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        OrderEntity.persist(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Order> findById(String id) {
        return OrderEntity.<OrderEntity>findByIdOptional(id).map(mapper::toDomain);
    }

    @Override
    public List<Order> findByCustomerId(String customerId) {
        return OrderEntity.<OrderEntity>list("customerId", customerId)
            .stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Order> findAll() {
        return OrderEntity.<OrderEntity>listAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(String id) {
        OrderEntity.deleteById(id);
    }
}
