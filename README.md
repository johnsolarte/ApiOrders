# Order API - Quarkus + Kafka + Arquitectura Hexagonal

## Stack
- Java 21, Quarkus 3.32.1, Apache Kafka, PostgreSQL
- Arquitectura Hexagonal, SOLID, Patrones de Diseno

## Levantar el proyecto

### 1. Infraestructura (Kafka + PostgreSQL)
```
docker-compose up -d
```

### 2. Ejecutar app

#### Opción 1: En host
```
mvn quarkus:dev
```

#### Opción 2: En Docker
```
docker-compose up -d
```
Esto inicia toda la infraestructura (PostgreSQL, Kafka, Kafka UI) y la app Quarkus en contenedores.

- App: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui
- Kafka UI: http://localhost:8090

## Endpoints

| Metodo | URL | Descripcion |
|--------|-----|-------------|
| POST | /api/orders | Crear pedido |
| GET | /api/orders | Listar todos |
| GET | /api/orders/{id} | Obtener por ID |
| GET | /api/orders/customer/{id} | Pedidos del cliente |
| PUT | /api/orders/{id}/confirm | Confirmar |
| PUT | /api/orders/{id}/cancel | Cancelar |
| PUT | /api/orders/{id}/process | Procesar |

## Ejemplo Request
```json
{
  "customerId": "cliente-001",
  "items": [
    { "productId": "prod-001", "productName": "Laptop", "quantity": 2, "unitPrice": 1500.00 }
  ]
}
```

## Estructura Hexagonal

```
domain/          <- Dominio puro (sin frameworks)
  model/         <- Order (Aggregate), OrderItem (Value Object)
  port/in/       <- Puertos de entrada (casos de uso)
  port/out/      <- Puertos de salida (repo, eventos)
application/     <- Servicios de aplicacion (orquestacion)
infrastructure/  <- Adaptadores
  adapter/in/rest/      <- REST (adaptador primario)
  adapter/out/kafka/    <- Kafka (adaptador secundario)
  adapter/out/persistence/ <- PostgreSQL (adaptador secundario)
```

## Patrones Implementados
- Factory Method: Order.create()
- Value Object: OrderItem (Java 21 Record)
- Aggregate Root: Order
- Repository Pattern
- Adapter Pattern (Kafka, JPA)
- DTO + Mapper

## Flujo de Creacion
```
POST /api/orders
  -> OrderResource (REST Adapter)
  -> CreateOrderService (Application)
  -> Order.create() (Domain)
  -> OrderRepository.save() -> PostgreSQL
  -> OrderEventPublisher.publish() -> Kafka topic
```
