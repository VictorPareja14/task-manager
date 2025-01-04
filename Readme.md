
# Task Manager API

## Description

The **Task Manager API** is a RESTful service designed to manage tasks. It allows users to create, update, delete, and retrieve tasks from a task management system. The application is built with modern web technologies, leveraging **Spring Boot** for the backend, with **JUnit** for unit testing, and **H2 in-memory database** for persistence during testing. Additionally, logging is managed via **Logback** for clean and structured logging.

## Features

- **Task Management**: Create, read, update, and delete tasks with ease.
- **Task Status**: Tasks can have different statuses such as "pending" or "done".
- **In-Memory Database**: Uses H2 as an in-memory database for test environments.
- **Comprehensive Logging**: Configured with **Logback** for structured and configurable logging.
- **Unit Testing**: Extensive unit tests covering service and controller layers.
- **Spring Boot**: The application is built with **Spring Boot**, leveraging its simplicity and robustness.

## Technologies & Versions

- **Spring Boot**: 3.4.1
- **JUnit 5**: 5.8.2
- **Mockito**: 4.6.1
- **Logback**: 1.2.11
- **H2 Database**: 2.1.214
- **Spring Data JPA**: 3.0.0
- **Spring Web**: 3.0.0
- **Swagger/OpenAPI**: 2.7.0
- **Lombok**: 1.18.36
- 
## Setup & Installation

### Prerequisites

- **JDK 22**
- **Maven 3.8.1 or later**
- An IDE such as **IntelliJ IDEA** or **Eclipse** is recommended

### Steps to Run Locally

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/task-manager-api.git
   cd task-manager-api
   ```

2. **Build the project**:
   Using Maven:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   Using Maven:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**:
   Once the application is running, the API can be accessed at:
   ```
   http://localhost:8080
   ```

   Use tools like **Postman** or **cURL** to test the endpoints.

### Swagger UI
If you want to interact with the API directly through a user-friendly interface, you can access the **Swagger UI** at:
```
http://localhost:8080/swagger-ui/
```

### Running Unit Tests

Unit tests can be executed with the following Maven command:
```bash
mvn test
```

The tests are located in the `src/test/java` folder. **Jacoco** is used for test coverage analysis, and the results can be found in `target/site/jacoco`.

## API Endpoints

### 1. **Get All Tasks**
- **Endpoint**: `GET /tasks`
- **Description**: Retrieve all tasks from the system.

### 2. **Get Task by ID**
- **Endpoint**: `GET /tasks/{id}`
- **Description**: Retrieve a task by its ID.

### 3. **Create Task**
- **Endpoint**: `POST /tasks`
- **Description**: Create a new task in the system.

### 4. **Update Task**
- **Endpoint**: `PUT /tasks/{id}`
- **Description**: Update an existing task by ID.

### 5. **Delete Task**
- **Endpoint**: `DELETE /tasks/{id}`
- **Description**: Delete a task by its ID.

### 6. **Get Tasks by Status**
- **Endpoint**: `GET /tasks/status/{status}`
- **Description**: Retrieve tasks filtered by their status (e.g., "pending", "done").

## Logging

Logging is handled through **Logback**, and can be configured in the `src/main/resources/logback-spring.xml` file.

## Unit Tests

Unit tests for the service and controller layers are written using **JUnit 5** and **Mockito**. Tests cover:

- Task creation, retrieval, updating, and deletion.
- Validations for task data and exception handling.
- Mocked interactions with the repository layer using Mockito.

### Code Coverage

Code coverage is measured using **JaCoCo**. After running tests, you can find the code coverage reports in the `target/site/jacoco/index.html` file.

## Contributing

We welcome contributions to improve this project. If you have suggestions or find any issues, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature-name`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature/your-feature-name`).
5. Create a new pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
