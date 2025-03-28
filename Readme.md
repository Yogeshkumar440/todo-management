# Todo Management API

## Overview
The **Todo Management API** is a RESTful web service built using **Spring Boot** that allows users to manage their tasks (todos). It supports user authentication and authorization using **JWT (JSON Web Token)**. The API provides role-based access control (RBAC), where only **admins** can modify or delete todos, while **users** can only retrieve and update their own tasks.

## Features
- User Authentication & Authorization with **JWT**
- Role-based access control (**ADMIN**, **USER**)
- CRUD operations for todos
- Mark todos as **complete/incomplete**
- Secure endpoints with **Spring Security**
- Uses **Spring Data JPA** and **Hibernate**

## Tech Stack
- **Spring Boot**
- **Spring Security & JWT**
- **Spring Data JPA (Hibernate)**
- **MySQL Database**
- **Lombok**
- **Maven**

## Installation & Setup

### 1. Clone the Repository
```sh
https://github.com/YOUR_GITHUB_USERNAME/todo-management.git
cd todo-management
```

### 2. Configure the Database
Update `application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Build and Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### **Authentication & User Management**
| Method | Endpoint         | Description                | Access |
|--------|----------------|----------------------------|--------|
| POST   | `/api/auth/register` | Register a new user       | Public |
| POST   | `/api/auth/login`    | Login and get JWT token  | Public |

### **Todo Management**
| Method | Endpoint           | Description                  | Access |
|--------|------------------|------------------------------|--------|
| POST   | `/api/todos`     | Create a new todo            | Admin  |
| GET    | `/api/todos/{id}` | Get a specific todo          | Admin, User |
| GET    | `/api/todos`     | Get all todos                | Admin, User |
| PUT    | `/api/todos/{id}` | Update a todo                | Admin  |
| DELETE | `/api/todos/{id}` | Delete a todo                | Admin  |
| PATCH  | `/api/todos/{id}/complete` | Mark a todo as complete | Admin, User |
| PATCH  | `/api/todos/{id}/in-complete` | Mark a todo as incomplete | Admin, User |

## Security & JWT Authentication
- After logging in, the server returns a JWT token.
- Include this token in the `Authorization` header for all protected requests:
  ```sh
  Authorization: Bearer YOUR_JWT_TOKEN
  ```

## Testing the API
You can test the API using **Postman** or **cURL**:

**Login Request:**
```sh
curl -X POST "http://localhost:8080/api/auth/login" -H "Content-Type: application/json" -d '{"usernameOrEmail": "user1", "password": "password123"}'
```

**Create Todo (Admin Only):**
```sh
curl -X POST "http://localhost:8080/api/todos" -H "Authorization: Bearer YOUR_JWT_TOKEN" -H "Content-Type: application/json" -d '{"title": "Complete project", "description": "Finish Spring Boot app"}'
```

## License
This project is open-source under the **MIT License**.

---
### Author
Developed by **Yogesh Kumar**

