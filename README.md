
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
