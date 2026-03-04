package com.example.order.infrastructure.adapter.in.rest;

import com.example.order.domain.model.Order;
import com.example.order.domain.port.in.CreateOrderUseCase;
import com.example.order.domain.port.in.GetOrderUseCase;
import com.example.order.domain.port.in.UpdateOrderStatusUseCase;
import com.example.order.infrastructure.adapter.in.rest.dto.CreateOrderRequest;
import com.example.order.infrastructure.adapter.in.rest.dto.OrderResponse;
import com.example.order.infrastructure.adapter.in.rest.mapper.OrderRestMapper;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * ADAPTADOR PRIMARIO (de entrada) - REST API.
 * ARQUITECTURA HEXAGONAL: traduce HTTP al lenguaje del dominio.
 * Solo conoce DTOs y casos de uso (puertos), NO la logica de negocio.
 * SOLID SRP: solo maneja el protocolo HTTP.
 */
@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final CreateOrderUseCase createOrder;
    private final GetOrderUseCase getOrder;
    private final UpdateOrderStatusUseCase updateStatus;
    private final OrderRestMapper mapper;

    @Inject
    public OrderResource(CreateOrderUseCase createOrder, GetOrderUseCase getOrder,
                          UpdateOrderStatusUseCase updateStatus, OrderRestMapper mapper) {
        this.createOrder = createOrder;
        this.getOrder = getOrder;
        this.updateStatus = updateStatus;
        this.mapper = mapper;
    }

    @POST
    public Response createOrder(@Valid CreateOrderRequest request) {
        Order order = createOrder.execute(mapper.toCommand(request));
        return Response.status(Response.Status.CREATED)
            .entity(mapper.toResponse(order)).build();
    }

    @GET
    public List<OrderResponse> getAllOrders() {
        System.out.println("Getting all orders");
        return mapper.toResponseList(getOrder.findAll());
    }

    @GET
    @Path("/{id}")
    public OrderResponse getOrderById(@PathParam("id") String id) {
        return mapper.toResponse(getOrder.findById(id));
    }

    @GET
    @Path("/customer/{customerId}")
    public List<OrderResponse> getOrdersByCustomer(@PathParam("customerId") String customerId) {
        return mapper.toResponseList(getOrder.findByCustomer(customerId));
    }

    @PUT
    @Path("/{id}/confirm")
    public OrderResponse confirmOrder(@PathParam("id") String id) {
        return mapper.toResponse(updateStatus.confirm(id));
    }

    @PUT
    @Path("/{id}/cancel")
    public OrderResponse cancelOrder(@PathParam("id") String id) {
        return mapper.toResponse(updateStatus.cancel(id));
    }

    @PUT
    @Path("/{id}/process")
    public OrderResponse processOrder(@PathParam("id") String id) {
        return mapper.toResponse(updateStatus.startProcessing(id));
    }
}
