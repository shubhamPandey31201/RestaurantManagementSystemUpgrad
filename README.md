
## Restaurant Management System

A comprehensive Restaurant Management System built with Java designed to streamline restaurant operations such as customer management, table booking, menu management, order processing, kitchen operations, sales reporting, and staff management.

---

### Features & Functionality

#### Customer Management
- **Add Customer:** Register new customers with name, phone, email, and address.
- **Update Customer:** Modify existing customer information.
- **View Customer:** Retrieve customer profiles by ID or list all customers.
- **Delete Customer:** Remove customer records from the system.

#### Table Booking
- **Book Table:** Reserve a specific table for a customer, specifying table number, booking time, and customer details.
- **View Bookings:** List all current and past table bookings.
- **Cancel Booking:** Remove or update a booking if the customer cancels or changes plans.

#### Menu Management
- **Add Menu Item:** Create new menu items with name, description, and price.
- **Update Menu Item:** Edit details of existing menu items.
- **View Menu:** List all available menu items.
- **Delete Menu Item:** Remove menu items that are no longer offered.

#### Order Management
- **Create Order:** Place a new order for a customer, including multiple menu items and quantities.
- **Add Order Item:** Add individual items to an order, specifying menu item, quantity, and total price.
- **View Orders:** Retrieve all orders or filter by customer, date, or status.
- **Update Order:** Modify order details before processing.
- **Delete Order:** Cancel or remove orders as needed.

#### Kitchen Operations
- **Create Kitchen Order:** Generate kitchen orders from placed orders, linking to order items.
- **Update Kitchen Order Status:** Change the status of kitchen orders (e.g., PREPARING, READY, SERVED).
- **View Kitchen Orders:** List all kitchen orders with their statuses and last updated times.

#### Sales Reporting
- **Generate Sales Report:** Create reports summarizing total revenue, total orders, and other sales metrics for a given period.
- **View Sales Reports:** Access generated reports for business analysis.
- **Export Reports:** Export reports for external use or record-keeping.

#### Staff Management
- **Add Waiter Details:** Register waiter information, including user ID and shift details.
- **Update Waiter Details:** Edit waiter records as needed.
- **View Waiter Details:** List all waiters and their assigned shifts.
- **Delete Waiter Details:** Remove waiter records if they leave or change roles.
- **User Management:** Add, update, and delete user accounts with roles (Admin, Staff).
- **Role Management:** Define and assign roles (ADMIN, STAFF) to users for access control.

---

Each feature is fully implemented and accessible through the system’s modular service structure. For more details, refer to the codebase.

---

## Technology Stack

- **Language:** Java 17+
- **Build Tool:** Maven
- **Database:** PostgreSQL
- **Logging:** SLF4j
- **IDE:** IntelliJ IDEA (recommended)

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 14+
- IntelliJ IDEA or any Java IDE

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/shubhamPandey31201/RestaurantManagementSystemUpgrad.git
cd RestaurantManagementSystemUpgrad.git
```
### 2. Configure the Database

- Create a PostgreSQL database named `restaurant_db`.
- Update the database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/restaurant_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```
# Build and Run Instructions

## 1. Build the Project

```bash
mvn clean package
```

This command compiles the code, runs tests, and packages the application into a JAR file located in the `target` directory.

## 2. Run the Application

```bash
java -jar target/restaurant-management-system-0.0.1-SNAPSHOT.jar
```

This command starts the application using the generated JAR file.

## ER Diagram

``` mermaid
erDiagram
    role {
        int role_id PK
        string role_name
    }

    user {
        int user_id PK
        string name
        string phone
        string email
        string password
        text address
        int role_id FK
    }

    customer {
        int customer_id PK
        string name
        string phone
        string email
        text address
    }

    restaurant_table {
        int table_id PK
        int capacity
        string location_description
    }

    table_booking {
        int booking_id PK
        int customer_id FK
        int table_id FK
        timestamp booking_time
        string status
        int number_of_guests
    }

    "order" {
        int order_id PK
        int customer_id FK
        int table_id FK
        int waiter_id FK
        timestamp order_timestamp
        string status
        timestamp payment_timestamp
    }

    menu_item {
        int menu_id PK
        string name
        text description
        decimal price
    }

    order_item {
        int order_item_id PK
        int order_id FK
        int menu_id FK
        int quantity
        decimal total_price
    }

    kitchen_order {
        int kitchen_order_id PK
        int order_item_id FK
        string status
        timestamp last_updated_time
    }

    sales_report {
        int report_id PK
        int generated_by_user_id FK
        timestamp generated_date
        decimal total_revenue
        int total_orders
    }

    waiter_details {
        int user_id PK
        string shift
    }

    %% Relationships
    user ||--o{ role : has
    user ||--o{ "order" : takes
    user ||--o{ sales_report : generates
    user ||--o| waiter_details : has

    customer ||--o{ table_booking : makes
    customer ||--o{ "order" : places

    restaurant_table ||--o{ table_booking : books
    restaurant_table ||--o{ "order" : used_for

    "order" ||--|{ order_item : contains
    order_item ||--|| menu_item : refers
    order_item ||--|| kitchen_order : processed_as

    role ||--|{ user : defines

```

## Class Diagram


``` mermaid
classDiagram

%% === Classes (Tables) ===

class Role {
  +int role_id
  +string role_name
}

class User {
  +int user_id
  +string name
  +string phone
  +string email
  +string password
  +text address
  +int role_id
}

class Customer {
  +int customer_id
  +string name
  +string phone
  +string email
  +text address
}

class RestaurantTable {
  +int table_id
  +int capacity
  +string location_description
}

class TableBooking {
  +int booking_id
  +int customer_id
  +int table_id
  +timestamp booking_time
  +string status
  +int number_of_guests
}

class Order {
  +int order_id
  +int customer_id
  +int table_id
  +int waiter_id
  +timestamp order_timestamp
  +string status
  +timestamp payment_timestamp
}

class MenuItem {
  +int menu_id
  +string name
  +text description
  +decimal price
}

class OrderItem {
  +int order_item_id
  +int order_id
  +int menu_id
  +int quantity
  +decimal total_price
}

class KitchenOrder {
  +int kitchen_order_id
  +int order_item_id
  +string status
  +timestamp last_updated_time
}

class SalesReport {
  +int report_id
  +int generated_by_user_id
  +timestamp generated_date
  +decimal total_revenue
  +int total_orders
}

class WaiterDetails {
  +int user_id
  +string shift
}

%% === Relationships ===

User --> Role : assigned to
User --> WaiterDetails : has details
User --> SalesReport : generates
User --> Order : serves

Customer --> TableBooking : books
Customer --> Order : places

RestaurantTable --> TableBooking : assigned to
RestaurantTable --> Order : serves

Order --> OrderItem : includes
OrderItem --> MenuItem : refers to
OrderItem --> KitchenOrder : prepared as

SalesReport --> User : generated by
WaiterDetails --> User : is for

```

## Flowchart TD 

``` mermaid
flowchart TD
    A[Customer seated] --> B[Waiter takes order]
    B --> C[Order created in system]
    C --> D[Order sent to kitchen]
    D --> E[Kitchen prepares items]
    E --> F[Order marked as Ready]
    F --> G[Waiter serves food]
    G --> H[Order status: Served]

```
``` mermaid
flowchart TD
    A[Order served] --> B[Waiter initiates billing]
    B --> C[System calculates total]
    C --> D[Bill presented to customer]
    D --> E[Customer pays bill]
    E --> F[Payment recorded in system]
    F --> G[Order status: Billed]

```
``` mermaid
flowchart TD
    A[Customer requests a table] --> B[Staff checks availability]
    B --> C{Table available?}
    C -- Yes --> D[Create table booking]
    D --> E[Assign table and booking time]
    E --> F[Status: Booked]

    C -- No --> G[Inform customer - no table available]

```
