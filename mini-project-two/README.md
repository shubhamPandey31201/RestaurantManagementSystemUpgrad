# Mini Project Two - Restaurant Management API

A Spring Boot RESTful API for managing users, table bookings, and orders in a restaurant system.  
Supports role-based access control for `ADMIN`, `CUSTOMER`, `WAITER`, and `MANAGER`.

## Features

- User registration, update, and deletion
- JWT-based authentication and authorization
- Role-based access for different endpoints
- Table booking and order management
- Exception handling and validation
- Unit and controller tests

## Technologies

- Java 17+
- Spring Boot
- Spring Security (JWT)
- Maven
- JPA (Hibernate)
- Mockito & JUnit 5 (Testing)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- PostgreSQL

### Setup

1. **Clone the repository:**

   cd mini_project_two

## 📦 Setup & Configuration

### 1. Configure `application.properties`
Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update

# JWT Secret Key (Base64 encoded)
jwt.secret=YOUR_BASE64_SECRET
jwt.expiration=3600000
```

### Generate a valid Base64 JWT secret

```sh 
openssl rand -base64 32
```

### Build & Run
```sh
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### Authentication
- `POST /login` — Login and receive JWT token

### Users
- `POST /users` — Create user (role required in body)
- `GET /users` — List all users
- `GET /users/{id}` — Get user by ID
- `PUT /users/{id}` — Update user
- `DELETE /users/{id}` — Delete user

### Table Bookings
- `GET /table-bookings` — List bookings (ADMIN, CUSTOMER, WAITER)
- `GET /table-bookings/{id}` — Get booking by ID
- `POST /table-bookings` — Create booking (CUSTOMER)
- `PUT /table-bookings/{id}` — Update booking (CUSTOMER, ADMIN)
- `DELETE /table-bookings/{id}` — Cancel booking (CUSTOMER, ADMIN)

### Orders
- `GET /orders` — List orders (public)
- `POST /orders` — Create order (WAITER)
- `PUT /orders/{id}` — Update order (WAITER, MANAGER)

