package com.example.order.infrastructure.adapter.in.rest;

import com.example.order.domain.exception.OrderNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.Map;

/** Adapta excepciones de dominio a respuestas HTTP. SOLID OCP. */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException ex) {
        if (ex instanceof OrderNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("error", ex.getMessage(), "timestamp", LocalDateTime.now().toString())).build();
        }
        if (ex instanceof IllegalStateException) {
            return Response.status(Response.Status.CONFLICT)
                .entity(Map.of("error", ex.getMessage(), "timestamp", LocalDateTime.now().toString())).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(Map.of("error", "Internal server error", "timestamp", LocalDateTime.now().toString())).build();
    }
}
