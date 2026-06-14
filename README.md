# temp-monitor

Aplicación de monitoreo de temperatura con arquitectura de **monolito modular** y **Shared Kernel**, construida con [Quarkus](https://quarkus.io/).

## Arquitectura

graph LR
    subgraph Kernel["kernel (código común)"]
        Security["security"]
        Exceptions["shared/exception"]
    end

    subgraph Module["módulo (ej: auth)"]
        API["api"]
        Core["core"]
        Infra["infrastructure"]
    end

    Module --> Kernel
    API --> Core --> Infra

Cada módulo mantiene su propia arquitectura limpia interna (`api | core | infrastructure`) y comparte código transversal a través del **kernel** (seguridad, errores).

## Stack tecnológico

- **Java 21** + **Quarkus 3.36**
- **PostgreSQL 15** + Hibernate ORM con Panache
- **SmallRye JWT** (generación y validación de tokens)
- **BCrypt** (hashing de contraseñas)
- **Docker** + Docker Compose
- **OpenTelemetry** (trazabilidad integrada)
- **Lombok**, **JUnit 5**, **Rest-Assured**

## Configuración

### 1. Variables de entorno (.env)

Copia `.env.example` a `.env` y ajusta los valores:

```bash
cp .env.example .env