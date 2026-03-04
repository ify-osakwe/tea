# Tea — Blog REST API

A modern, production-ready **REST API for a blog application** built with Spring Boot 3.4. Tea provides secure, well-structured endpoints for managing posts, comments, categories, and user authentication — complete with email verification, JWT-based security, and interactive API documentation.

---

## Key Features

- **JWT Authentication & Email Verification** — Secure user registration with email-based OTP verification, login with JWT access tokens, and role-based access control via Spring Security.
- **Full Blog CRUD Operations** — Create, read, update, and delete posts with support for pagination, sorting, and category-based filtering.
- **Commenting System** — Threaded comments tied to individual posts, with full CRUD support.
- **Category Management** — Organize posts under user-defined categories for structured content navigation.
- **Interactive API Documentation** — Auto-generated Swagger UI powered by SpringDoc OpenAPI, available at `/swagger-ui/index.html`.

---

## 🛠️ Tech Stack

| Layer                | Technology                                               |
| -------------------- | -------------------------------------------------------- |
| **Language**         | Java 21                                                  |
| **Framework**        | Spring Boot 3.4.0                                        |
| **Persistence**      | Spring Data JPA + Hibernate                              |
| **Database**         | PostgreSQL 16                                            |
| **Migrations**       | Flyway                                                   |
| **Security**         | Spring Security + JWT (jjwt 0.12.6)                      |
| **Validation**       | Jakarta Bean Validation (spring-boot-starter-validation) |
| **Email**            | Spring Boot Mail (SMTP)                                  |
| **API Docs**         | SpringDoc OpenAPI 2.7.0 (Swagger UI)                     |
| **Build Tool**       | Maven (with Maven Wrapper)                               |
| **Containerization** | Docker (multi-stage build) + Docker Compose              |

---

## Prerequisites

Before you begin, ensure you have the following installed:

| Requirement             | Version                                               |
| ----------------------- | ----------------------------------------------------- |
| **Java JDK**            | 21 or higher                                          |
| **Maven**               | 3.9+ _(or use the included Maven Wrapper — `./mvnw`)_ |
| **PostgreSQL**          | 16+ _(or use Docker Compose)_                         |
| **Docker** _(optional)_ | 20.10+                                                |

---

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/ify-osakwe/tea.git
cd tea
```

### 2. Configure Environment Variables

Create a `.env` file in the project root with the following keys:

```properties
# Database
DB_URL=jdbc:postgresql://localhost:5432/tea_db
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# JWT
JWT_SECRET=your_base64_encoded_secret_key
JWT_EXPIRATION=86400000

# SMTP (Email Verification)
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USERNAME=your_email@gmail.com
SMTP_PASSWORD=your_app_password
```

> [!NOTE]
> The application uses `spring.config.import=optional:file:.env[.properties]` to automatically load the `.env` file. No additional configuration is needed.

### 3. Create the Database

If running PostgreSQL locally:

```bash
createdb tea_db
```

> [!TIP]
> **Flyway** will automatically run all migrations in `src/main/resources/db/migration/` on application startup, creating the required tables (`posts`, `comments`, `categories`, `roles`, `users`).

### 4. Run the Application

**Using Maven Wrapper (recommended):**

```bash
./mvnw spring-boot:run
```

**Or using Docker Compose** (includes PostgreSQL):

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`.

### 5. Explore the API

Open the interactive Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

**Live Demo:** [tea-swagger-doc](https://tea-w1fs.onrender.com/swagger-ui/index.html)

---

## Architecture Overview

Tea follows a **modular, layered architecture** where each domain feature is encapsulated as an independent module with its own controller, service, repository, and model layers.

### Request Lifecycle

```
Client Request
    → JwtAuthenticationFilter (validates Bearer token)
    → SecurityFilterChain (authorization rules)
    → @RestController (route handling + validation)
    → @Service (business logic)
    → @Repository (Spring Data JPA → PostgreSQL)
    → Response (DTO) → Client
```

**Key architectural decisions:**

- **Stateless sessions** — `SessionCreationPolicy.STATELESS` ensures no server-side session storage; all auth state is carried in the JWT.
- **Flyway migrations** — Schema is version-controlled; Hibernate is set to `validate` mode only (no auto-DDL).
- **Global exception handling** — `@ControllerAdvice` provides consistent error response format across all endpoints.
- **Constructor injection** — All dependencies use constructor injection (no `@Autowired` on fields).

---

## Docker

### Build & Run with Docker Compose

```bash
docker compose up --build
```

This starts both the **Tea API** (port `8080`) and a **PostgreSQL 16** instance, with a persistent volume for database storage. The app container waits for the database health check before starting.

> [!NOTE]
> The Dockerfile uses a **multi-stage build** (dependency caching → packaging → layer extraction → minimal JRE runtime) for optimized image size and build performance.

---
