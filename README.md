# User Management Microservice

This project is a microservice built with **Spring Boot 2.5.14** and **Gradle 7.4** for user creation and retrieval. The
microservice provides two main endpoints:

1. **/sign-up**: Enables user creation. It validates email and password formats, persists data in an H2 database, and returns
   user information along with a JWT token.
2. **/login**: Allows retrieving user data using a JWT token. The token is updated during each query.

## Components diagram

## Sequence diagram

## Running the application

```sh
git clone https://github.com/jdavidtorres/api-jwt.git
cd api-jwt
```

#### Using Gradle command line

```bash
gradle bootRun
```

#### Using Docker

```bash
docker build -t api-jwt .
docker run -p 8072:8072 api-jwt
```

You can check with the actuator endpoint if the application is running:
[http://localhost:8072/actuator/health](http://localhost:8072/actuator/health)

### OpenAPI documentation

Swagger UI is available at [http://localhost:8072/swagger-ui.html](http://localhost:8072/swagger-ui.html)
OpenAPI description is available at [http://localhost:8072/v3/api-docs](http://localhost:8072/v3/api-docs)

---
