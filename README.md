# ZakksTasks - Java Code Execution Playground

## Overview

This project is a code playground that allows users to submit Java code, which is then executed in a secure, sandboxed environment using Docker containers. The primary goal is to execute Java code safely and efficiently while providing feedback on the code execution status, output, and errors.

## Features

- Execute Java code in a secure Docker container
- Validate and format submitted code before execution
- Check for unsafe or potentially malicious code
- Returns execution results (success, failure, or error message)

## Technologies Used

- **Java 17** (or newer)
- **Spring Boot** for the backend framework
- **Docker** for sandboxed code execution
- **Maven** for dependency management
- **JUnit** and **Mockito** for testing

## Project Setup

### Build and run the project locally
1. **Docker Image Setup**: Ensure that Docker is running on your machine.
2. **Build the project**: Run the following command to build the application:
    ```bash
    mvn clean install
    ```
3. **Run the application**: Use the following command to run the Spring Boot application:
    ```bash
    mvn spring-boot:run
    ```

The application will be available at `http://localhost:8080`.

### Running the API
You can interact with the API by sending HTTP POST requests to `/execute` with your Java code as the body.

**Example Request** (using Postman):
1. Open Postman and set the request type to `POST`.
2. Set the URL to `http://localhost:8080/execute`.
3. In the **Body** tab, select `raw` and set the type to `JSON`.
4. Use the following Java code in the body:
    ```json
    {
      "code": "public class Test { public static void main(String[] args) { System.out.println(\"Hello World\"); }}"
    }
    ```

### API Response Example
```json
{
  "status": "Success",
  "output": "Hello World",
  "code": "public class Test { public static void main(String[] args) { System.out.println(\"Hello World\"); }}"
}
```

#### Error Example (Invalid Code):
```json
{
  "status": "Failure",
  "message": "Code contains syntax errors."
}
```
#### Error Example (Unsafe Code):
```json
{
  "status": "Failure",
  "message": "Unsafe code detected."
}
```

## Testing

The project comes with a comprehensive suite of unit and integration tests. To run the tests, execute the following command:
```bash
mvn test
```
#### Example Tests:
- Execute Valid Java Code
- Execute Code with Invalid Syntax
- Check for Unsafe Code

## Docker Configuration
- Memory Limit: 256 MB
- CPU Limit: 0.5 CPUs
- Network: Disabled for security

These limits ensure that user-submitted code cannot consume excessive resources or interact with external networks.

## Security Considerations
- The code execution is sandboxed in a Docker container to prevent potentially dangerous interactions with the host system.
- Only whitelisted Java code patterns are allowed to prevent malicious activities such as file I/O, network access, or infinite loops.
- 
