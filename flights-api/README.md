
# Flights API

CRUD de vuelos realizado en el marco del Bootcamp Java de Codeki.


## Sobre el proyecto

Desarrollado en Java (OpenJDK 17) y Spring Boot v.3.2.3

## Base de datos

La API utiliza MySQL como sistema de gestión de bases de datos. Se puede acceder a su configuración en `application.yml`.

Por defecto, para ejecutar el proyecto es necesario crear un esquema vacío con el nombre `flightsDB`.


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
[Ver documentación](https://petstore.swagger.io/?url=https://raw.githubusercontent.com/irinaponzi/flightsAPI/main/src/main/resources/swagger.yaml)

