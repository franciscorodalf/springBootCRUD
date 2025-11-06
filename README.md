# Gestión de hotel con Spring Boot y SQLite

Aplicación Spring Boot de ejemplo que ilustra cómo construir una capa de persistencia limpia para un dominio de reservas hoteleras usando JPA y SQLite. El proyecto está pensado como material de docencia: modela entidades básicas (Hotel, Room, Guest, Booking), expone repositorios JPA especializados y carga datos de demostración al iniciar la aplicación.

## Tecnologías principales
- Java 17
- Spring Boot 3.2 (starter, data-jpa, testing)
- SQLite con dialecto `hibernate-community-dialects`
- Maven como gestor de build
- JaCoCo para medición de cobertura (mínimo configurado: 70 %)

## Arquitectura y modelo de dominio
- **Capas**: dominio (entidades + contratos `repository`) y persistencia (`persistence.jpa` con repositorios concretos sobre `EntityManager`). Aún no existe capa web, lo que permite concentrarse en patrones de persistencia.
- **Entidades**:
  - `Hotel`: agrega habitaciones (`Room`) y se identifica por `id`.
  - `Room`: pertenece a un hotel, define tipo y tarifa, y expone reservas asociadas.
  - `Guest`: datos de contacto del huésped.
  - `Booking`: relaciona habitación y huésped con fechas `checkIn`/`checkOut`.
- **Repositorios JPA**: cada agregado dispone de una implementación que extiende `AbstractJpaRepository`, lo que evita duplicar operaciones CRUD comunes y permite añadir consultas específicas (por ejemplo, habitaciones por hotel o reservas en un rango de fechas).

## Carga de datos inicial
El componente `DataLoader` (`CommandLineRunner`) inserta automáticamente:
- Hotel **H1** – “Hotel Puerto”
- Habitación **R1** – Suite 101 (95 €/noche)
- Huésped **G1** – Carlos (carlos@example.com)
- Reserva **B1** – del 5 al 10 de noviembre de 2025

La base de datos se crea en `src/main/resources/hotel_puerto.db`, pero también se puede trabajar en memoria durante las pruebas.

## Requisitos previos
- Java 17 instalado y disponible en `JAVA_HOME`
- Maven 3.9+
- No se requiere un servidor de base de datos externo; SQLite se gestiona en local.

## Puesta en marcha
Desde la carpeta `docencia-notes`:

```bash
mvn spring-boot:run
```

La aplicación iniciará y ejecutará `DataLoader`, dejando la base de datos lista para ser consultada mediante futuras capas (REST, CLI, etc.).

### Base de datos
- URI configurada: `jdbc:sqlite:src/main/resources/hotel_puerto.db`
- Generación de esquema: `spring.jpa.hibernate.ddl-auto=create`
- SQL visible en consola: activar/desactivar con `spring.jpa.show-sql`

## Pruebas y cobertura
Ejecuta la suite de persistencia con:

```bash
mvn clean test
```

Los tests `@DataJpaTest` usan una base SQLite en memoria compartida y verifican operaciones CRUD para cada repositorio. JaCoCo genera el informe HTML en `target/site/jacoco/index.html` y falla el build si la cobertura de líneas bajase del 70 %.

## Estructura del proyecto
```
docencia-notes/
├── pom.xml
├── src
│   ├── main
│   │   ├── java/com/docencia/…
│   │   │   ├── Application.java
│   │   │   ├── hotel/DataLoader.java
│   │   │   ├── hotel/domain/model/*.java
│   │   │   ├── hotel/domain/repository/*.java
│   │   │   └── hotel/persistence/jpa/*.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── hotel_puerto.db
│   └── test/java/com/docencia/hotel/persistence/jpa/*.java
└── target/…
```

## Próximos pasos sugeridos
1. Añadir controladores REST o GraphQL para exponer la funcionalidad.
2. Extender validaciones de dominio (reglas de solapamiento de reservas, tarifas dinámicas).
3. Incorporar pruebas de integración end-to-end una vez exista la capa web.
