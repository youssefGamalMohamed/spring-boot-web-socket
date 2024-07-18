# Spring Boot Web-socket
This Repo Contains Applied Example for Learning Web-Sockets using Spring Boot

## Features

- **WebSocket Endpoints**
   - **/addition**
      - Endpoint for performing iterative addition operations and each time response to client with the new addition response
      - **Request Body Format:**
        ```json
        {
          "number": 1,
          "addedValue": 1,
          "iterations": 1
        }
        ```
      - **Response Format:**
        ```json
        {
          "result": 1
        }
        ```
   - **/chat**
      - Endpoint for real-time messaging between multiple users.
      - Supports text-based messages and user authentication.
      - Provides a WebSocket connection for instant message delivery.






## Technologies Used

- **Spring Boot** - Backend framework for easy Java application development
- **WebSocket API** - Enables real-time communication between client and server

## Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/youssefGamalMohamed/spring-boot-web-socket.git
   ```

2. **Navigate to the project directory**
   ```bash
   cd spring-boot-web-socket
   ```

3. **Build the project**
   ```bash
   ./mvnw clean package
   ```

4. **Run the application**
   ```bash
   java -jar target/spring-boot-web-socket-<version>.jar
   ```

5. **Access the application**
   Open a web browser and go to `http://localhost:8080`

