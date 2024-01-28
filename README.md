# Zoo Management System

## Description

The Zoo Management System is a comprehensive backend solution for effectively managing zoo operations. Built with Spring Boot 3.2.2 and MongoDB, this system is designed to handle various zoo management tasks such as ticket management, event scheduling, user profile management, and user authentication (login and registration). It's a robust and scalable platform, ensuring smooth operations for zoos of any size.

## Features

- **Ticket Management**: Allows efficient handling of ticket sales, including tracking, reporting, and managing ticket availability.
- **Event Management**: Facilitates the organization and scheduling of zoo events, including public events, private tours, and educational programs.
- **Profile Management**: Enables zoo staff and visitors to manage their profiles, including personal information and preferences.
- **Login and Registration**: Secure authentication system for user login and registration, ensuring data security and privacy.

## Technical Stack

- **Spring Boot 3.2.2**: For building the standalone application.
- **MongoDB**: NoSQL database for scalable data storage.
- **Spring Security**: For robust authentication and authorization.
- **Lombok**: To reduce boilerplate code for model objects.
- **Spring Boot Starter Web**: For creating RESTful web applications.
- **Spring Boot Starter Data MongoDB**: To integrate MongoDB database.
- **Spring Boot Starter Test**: For unit and integration testing.
- **Spring Security Test**: For testing security configurations.

## Prerequisites

- Java 17 or higher
- Maven 3.2 or higher
- MongoDB server (local or remote)

## Installation

1. **Clone the repository:**
   ```sh
   git clone [repository URL]
   ```

2. **Navigate to the project directory:**
   ```sh
   cd zooManagment
   ```

3. **Build the project using Maven:**
   ```sh
   mvn clean install
   ```

4. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```

## Configuration

- **Application Properties**: Configure application settings in `src/main/resources/application.properties`.
- **MongoDB Settings**: Set your MongoDB URI and database name in the application properties file.
- **Security Configurations**: Adjust Spring Security settings as per your security requirements.

## Usage

Once the application is running, it will serve the REST API endpoints for managing tickets, events, user profiles, and authentication.

- Use API clients like Postman or Swagger for interacting with the API.
- The API documentation can be accessed at `[Base URL]/swagger-ui.html` (if Swagger is integrated).

## Contributing

Contributions to the Zoo Management System are welcome. Please follow the standard fork-and-pull request workflow.

- **Fork** the repository
- **Create** your feature branch (`git checkout -b feature/AmazingFeature`)
- **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
- **Push** to the branch (`git push origin feature/AmazingFeature`)
- **Open** a pull request

## License

Distributed under the MIT License. See `LICENSE` file for more information.