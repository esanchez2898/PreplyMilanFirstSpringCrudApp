# SpringCRUDApp

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?logo=apachemaven)

> A RESTful CRUD API built with **Spring Boot 4** for managing products, connected to a **PostgreSQL** database.
> Follows a clean layered architecture: Controller → Service → Repository, with DTO/Entity separation and centralized exception handling.
>
> Built as a learning project to practice Spring Boot best practices.

---

## Tech Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.3 |
| Spring Data JPA | 4.0.3 |
| PostgreSQL Driver | 42.7.10 |
| ModelMapper | 3.2.6 |
| Lombok | 1.18.44 |
| Hibernate Validator | 9.1.0.Final |
| Jakarta Validation API | 3.1.1 |

---

## Project Structure

```
src/main/java/com/erick/spring/
│
├── config/
│   └── SpringbootConfiguration.java     # App config, ModelMapper bean
│
├── controller/
│   ├── ProductController.java           # REST endpoints
│   └── ErrorsController.java           # Global exception handler
│
├── converter/
│   └── ProductConverter.java           # DTO ↔ Entity mapping
│
├── dto/
│   └── ProductDTO.java                 # Data Transfer Object (API layer)
│
├── entity/
│   └── ProductEntity.java              # JPA Entity (DB layer)
│
├── exception/
│   ├── DataNotValidateException.java
│   ├── ExistingInstanceException.java
│   └── InstanceUndefinedException.java
│
├── repository/
│   └── ProductRepository.java          # JPA Repository
│
└── service/
    ├── ProductService.java             # Service interface
    └── impl/
        └── ProductServiceImpl.java     # Business logic
```

---

## Architecture Overview

```
Client (Postman / Frontend)
        │
        ▼
  [Controller]          ← Receives HTTP requests, returns responses
        │
        ▼
  [Service]             ← Business logic, validations, rules
        │
   ┌────┴────┐
   ▼         ▼
[Repository] [Converter]   ← DB access / DTO ↔ Entity translation
   │
   ▼
[PostgreSQL DB]
```

---

## Execution Flow

When the application starts and receives a request, it follows this order:

**1. Application Startup**
Spring Boot scans all components (`@SpringbootConfiguration`) and registers the beans,
including `ModelMapper`, repositories, services, and controllers.

**2. Request enters the Controller**
`ProductController` receives the HTTP request. If the endpoint expects a body (POST, PUT),
the `@Validated` annotation triggers field validation using the constraints defined in `ProductDTO`
(`@NotBlank`, `@Size`, `@DecimalMax`, etc.).

**3. Validation check**
If validation fails → `DataNotValidateException` is thrown immediately.
`ErrorsController` catches it and returns `400 Bad Request`.

**4. Controller calls the Service**
`ProductController` calls the corresponding method on `ProductService` (the interface).
Spring injects `ProductServiceImpl` automatically.

**5. Service applies business logic**
`ProductServiceImpl` checks rules before touching the database:
- On **add**: checks if a product with the same name already exists → throws `ExistingInstanceException` (409) if so.
- On **update / delete**: verifies the product exists by ID → throws `InstanceUndefinedException` (404) if not.

**6. Service uses the Converter**
`ProductConverter` translates the incoming `ProductDTO` → `ProductEntity` (before saving)
or `ProductEntity` → `ProductDTO` (before returning the response), using `ModelMapper`.

**7. Service calls the Repository**
`ProductRepository` (extends `JpaRepository`) executes the SQL query against the PostgreSQL database.
All write operations are wrapped in `@Transactional`; reads use `@Transactional(readOnly = true)`.

**8. Response travels back**
Entity → Converter → DTO → Service → Controller → HTTP Response to the client.

### Request lifecycle (simplified)

```
HTTP Request
    │
    ▼
[ProductController]  →  @Validated (ProductDTO)
    │                        │
    │                   Validation fails?
    │                        │
    │                   DataNotValidateException → 400
    │
    ▼
[ProductServiceImpl]
    ├── Business rule check
    │       ├── ExistingInstanceException → 409
    │       └── InstanceUndefinedException → 404
    │
    ├── [ProductConverter]  DTO → Entity
    │
    ├── [ProductRepository] → PostgreSQL
    │
    └── [ProductConverter]  Entity → DTO
    │
    ▼
HTTP Response (DTO as JSON)
```

---

## API Endpoints

Base URL: `/api/products`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/products/{id}` | Get product by ID |
| `GET` | `/api/products/name/{name}` | Get product by name |
| `POST` | `/api/products` | Create a new product |
| `PUT` | `/api/products/{id}` | Update an existing product |
| `DELETE` | `/api/products/{id}` | Delete a product by ID |

---

## Request / Response

### ProductDTO fields

| Field | Type | Constraints |
|-------|------|-------------|
| `id` | Integer | Optional (auto-generated) |
| `name` | String | Not blank, 3–50 chars, unique |
| `price` | Double | Not null, 0–10000 |
| `description` | String | Not empty, 10–100 chars |

### Example — Create Product (POST `/api/products`)

**Request Body:**
```json
{
  "name": "Laptop",
  "price": 999.99,
  "description": "A high-performance laptop for developers"
}
```

**Response:** `201 Created`
```
Product was added to the db
```

---

## Exception Handling

Global exception handling is managed by `ErrorsController` using `@RestControllerAdvice`:

| Exception | HTTP Status | Trigger |
|-----------|-------------|---------|
| `DataNotValidateException` | `400 Bad Request` | Validation failure on request body |
| `ExistingInstanceException` | `409 Conflict` | Product with the same name already exists |
| `InstanceUndefinedException` | `404 Not Found` | Product not found by ID or name |

---

## Database Configuration

The app connects to a local PostgreSQL database. Configure your credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/online_store
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

> **Note:** The `ddl-auto=update` setting lets Hibernate automatically create or update the `products` table on startup.

---

## Running Locally

### Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL running locally

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/SpringCRUDApp.git
   cd SpringCRUDApp
   ```

2. Create the database in PostgreSQL:
   ```sql
   CREATE DATABASE online_store;
   ```

3. Update your credentials in `application.properties`.

4. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. Test the API at `http://localhost:8080/api/products`

---

## Key Design Decisions

- **Interface-based services:** `ProductController` depends on the `ProductService` interface, not the implementation. This allows swapping implementations (e.g., adding a cached version) without touching the controller.
- **DTO/Entity separation:** `ProductDTO` controls what data is exposed externally; `ProductEntity` handles the database structure. `ModelMapper` handles the mapping via `ProductConverter`.
- **Constructor injection:** Lombok's `@RequiredArgsConstructor` is used throughout instead of `@Autowired` for cleaner, testable code.
- **Transactional control:** Read operations use `@Transactional(readOnly = true)` for performance; write operations use `@Transactional` to ensure data consistency.
