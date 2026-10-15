# Spring Cloud Microservice Demo

A simple microservices example built with Spring Boot and Spring Cloud. It shows how a gateway can discover and route requests to backend services using Eureka service discovery.

## What this project demonstrates

- Spring Cloud Gateway as the entry point
- Netflix Eureka as the service registry
- A Content microservice that exposes a REST API
- Service discovery and load-balanced routing
- A minimal architecture for learning microservices patterns

---

## Architecture

The flow is:

Client -> API Gateway -> Eureka Service Discovery -> Content Service -> Response

In this demo:
- The Gateway listens on port 8001
- Eureka runs on port 8761
- The Content service runs on port 8002

---

## Services

### 1) Eureka Service Registry

This acts as the central registry where services register themselves.

Configuration:

```yaml
server:
  port: 8761

spring:
  application:
    name: service-registry

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

Dashboard:

```bash
http://localhost:8761
```

---

### 2) API Gateway

The gateway receives incoming traffic and routes it to registered services using Spring Cloud Gateway.

Example configuration:

```yaml
server:
  port: 8001

spring:
  main:
    web-application-type: reactive

  application:
    name: api-gateway

  cloud:
    gateway:
      routes:
        - id: content-service
          uri: lb://CONTENT
          predicates:
            - Path=/content/**
          filters:
            - StripPrefix=0

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

Key idea:
- `lb://CONTENT` tells the gateway to resolve the service from Eureka using load balancing.
- `Path=/content/**` matches requests such as `/content/welcome`.

---

### 3) Content Service

This service is a simple REST service registered in Eureka.

Example configuration:

```yaml
server:
  port: 8002
  servlet:
    context-path: /content

spring:
  application:
    name: content

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    instance:
      preferIpAddress: true
```

Sample controller:

```java
@RestController
@RequestMapping("/content")
public class ContentController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Content Service!";
    }
}
```

---

## Run the project

Start in this order:

1. Eureka Service Registry
2. Content Service
3. API Gateway

### 1. Start Eureka

```bash
http://localhost:8761
```

### 2. Start Content Service

The service registers itself in Eureka as:

```text
CONTENT
```

### 3. Start API Gateway

The gateway connects to Eureka and finds the content service dynamically.

### 4. Send a request

```bash
curl http://localhost:8001/content/welcome
```

Expected response:

```text
Welcome to Content Service!
```

---

## Internal flow

```text
Client Request
    |
    v
Gateway Route Matching
    |
    v
Eureka Discovery Client
    |
    v
Resolve CONTENT Instance
    |
    v
Forward Request
    |
    v
Content Service Response
```

---

## Technology stack

- Java 17+
- Spring Boot
- Spring Cloud Gateway
- Spring WebFlux
- Netflix Eureka
- Maven / Gradle

---

## Why this architecture is useful

- Centralized routing
- Dynamic service discovery
- Better separation of concerns
- Load balancing support
- Simple microservice learning model

---

## Suggested future improvements

- JWT authentication
- Spring Cloud Config Server
- Distributed tracing
- Resilience4j circuit breaker
- Docker and Kubernetes deployment
- API gateway rate limiting

---

## Author

Krishnamoorthy
