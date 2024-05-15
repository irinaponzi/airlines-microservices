
# Airlines microservices

Proyecto de microservicios realizado en el marco del Bootcamp Java de Codeki.


## Sobre el proyecto

Desarrollado en Java (OpenJDK 17) y Spring Boot v.3.2.3

## Base de datos

Los microservicios utilizan MySQL como sistema de gestión de bases de datos. Se puede acceder a su configuración en el directorio `config-data`.

## Json para Postman
*Flight:*
```json
{
    "origin": "String",
    "destiny": "String",
    "departureTime": "2024-01-01T10:00:00",
    "arrivingTime": "2024-01-01T10:00:00",
    "price": 0,
    "frequency": "String"
}
```
*Company:*
```json
{
    "name": "String",
    "page": "String",
    "banner": "String"
}
```

## Documentación en Swagger
[Documentación Auth Service](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/irinaponzi/airlines-microservices/main/auth-service/src/main/resources/swagger.yml)

[Documentación Flights API](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/irinaponzi/airlines-microservices/main/flights-api/src/main/resources/swagger.yml)

[Documentación Tickets API](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/irinaponzi/airlines-microservices/main/tickets-api/src/main/resources/swagger.yml)




