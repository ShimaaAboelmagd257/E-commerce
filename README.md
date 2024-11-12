# E-commerce Backend Application


## Overview
This is the backend of an E-commerce application built using Spring Boot. It provides RESTful APIs for managing users, products, carts, orders, and payments. The project is designed with a focus on scalability, security, and ease of use.

## Features
- **Microservices Architecture:**
  - The application is structured into distinct services, each responsible for a specific functionality such as user management, product catalog, cart, and order processing.

- **Kafka for Event-Driven Communication:**
 - Kafka is used to enable seamless asynchronous communication between services (e.g., product updates, inventory checks).
 - 
- **User Management:**
  - Registration, login, and profile management for users.

- **Authentication:**
  - Secure API access using JSON Web Tokens (JWT) for authentication and authorization.

- **Product Management:**
  - CRUD operations for managing products in the store.

- **Category Management:**
  - Manage and organize products into categories.

- **Cart Management:**
  - Add, remove, and view items in the cart.
  - Check inventory availability before checkout.
  - Calculate total price of items in the cart.
  - Remove all items from the cart in one action.

- **Order Management:**
  - Place and track orders.
  - Manage ordered items associated with each order.

- **Discount Management:**
  - Apply discounts to products or orders to provide special offers.

- **Payment Processing:**
  - Secure payment integration to handle transactions safely.

- **Shipping Management:**
  - Handle shipping options and track shipment status.

- **Rating and Reviews:**
  - Allow users to rate and review products to provide feedback and insights.

- **Watchlist Management:**
  - Users can add products to a watchlist to track items of interest.

- **Database Integration:**
  - Integrated with PostgreSQL for data persistence and retrieval.

    
## Technologies Used
- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security with JWT**
- **Kafka** (for asynchronous inter-service communication)
- **Pagination** (for API responses, especially in product listings)
- **PostgreSQL**
- **Maven**
- **Docker** (for environment setup)
- **JUnit** (for testing)

## Getting Started

### Prerequisites
- Java 17
- Maven
- PostgreSQL
- kafka
- Docker (optional)

### Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or features you'd like to add.
License

### For any inquiries or feedback, please reach out to shimaaaboelmagd257@gmail.com

