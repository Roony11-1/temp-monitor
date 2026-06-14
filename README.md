# temp-monitor

Aplicación de monitoreo de temperatura con arquitectura de **monolito modular** y **Shared Kernel**, construida con [Quarkus](https://quarkus.io/).

## Arquitectura
temp-monitor/
├── kernel/ ← Código común compartido entre módulos
│ ├── security/ ← JWT, PasswordHasher, TokenUser, Roles
│ └── shared/exception/ ← Manejo centralizado de errores (AppException, ErrorCode, mappers)
└── modules/
└── auth/ ← Módulo de autenticación y gestión de usuarios
├── api/ ← Adaptadores de entrada (REST, DTOs)
├── core/ ← Lógica de negocio (servicios, modelo de dominio, puertos)
└── infrastructure/ ← Adaptadores de salida (repositorios Panache, etc.)

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