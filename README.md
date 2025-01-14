### Descripción en Español

#### Description in English

This project is a microservice built with **Spring Boot 2.5.14** and **Gradle 7.4** for user creation and retrieval. The
microservice provides two main endpoints:

1. **/sign-up**: Enables user creation. It validates email and password formats, persists data in an H2 database, and returns
   user information along with a JWT token.
2. **/login**: Allows retrieving user data using a JWT token. The token is updated during each query.

The microservice meets the following requirements:

- Implements unit tests with a minimum coverage of 80%.
- All inputs and outputs are in JSON format.
- Handles exceptions and returns appropriate HTTP codes.
- Leverages Java 8 or 11 features, such as lambdas and streams.

---

#### Using Java

1. Ensure **Java 8 or 11** and **Gradle** are installed on your system.
2. Clone the project repository:
   ```bash
   git clone <REPOSITORY_URL>
   cd <PROJECT_NAME>
   ```
3. Build the project:
   ```bash
   ./gradlew build
   ```
4. Run the application:
   ```bash
   java -jar build/libs/<JAR_NAME>.jar
   ```
5. Access the API at `http://localhost:8080`.

---
