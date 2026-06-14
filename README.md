# Spring Cloud Microservices Demo

This project demonstrates a simple microservices architecture using:

- Spring Cloud Gateway (WebFlux)
- Eureka Service Registry
- Content Microservice
- Reactive Routing with Load Balancing

---

# Architecture Overview

<img width="1536" height="1024" alt="ChatGPT Image May 27, 2026, 08_30_19 PM" src="https://github.com/user-attachments/assets/27670cc9-e2e5-45c6-bdbf-57b4f12cc172" />


---

# Services

## 1. Eureka Service Registry

Acts as the central registry where all services register themselves.

### Configuration

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

### Access Eureka Dashboard

```bash
http://localhost:8761
```

---

## 2. API Gateway (Spring Cloud Gateway WebFlux)

Handles all incoming requests and routes them to downstream services.

### Features

- Reactive WebFlux Gateway
- Service Discovery using Eureka
- Load Balanced Routing
- Route Predicates & Filters

### Configuration

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
      server:
        webflux:
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

logging:
  level:
    org.springframework.cloud.gateway.server.webflux: TRACE
    org.springframework.cloud.gateway: TRACE
```

### Route Explanation

| Property | Description |
|---|---|
| `lb://CONTENT` | Load balanced Eureka service lookup |
| `Path=/content/**` | Match incoming `/content` requests |
| `StripPrefix=0` | Keeps original request path |

---

## 3. Content Service

Simple REST microservice registered with Eureka.

### Configuration

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
      defaultZone : http://localhost:8761/eureka/

    instance:
      preferIpAddress: true
```

### Sample Controller

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

# How It Works

## Step 1 — Start Eureka Server

```bash
http://localhost:8761
```

All services register here.

---

## Step 2 — Start Content Service

The Content Service registers itself in Eureka as:

```text
CONTENT
```

---

## Step 3 — Start API Gateway

Gateway connects to Eureka and dynamically discovers services.

---

## Step 4 — Send Request

```http
GET http://localhost:8001/content/welcome
```

---

# Internal Flow

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

# Expected Response

```text
Welcome to Content Service!
```

---

# Service Registration Example

Inside Eureka Dashboard:

| Application | Instances |
|---|---|
| API-GATEWAY | localhost:8001 |
| CONTENT | localhost:8002 |

---

# Technology Stack

- Java 17+
- Spring Boot
- Spring Cloud Gateway
- Spring WebFlux
- Netflix Eureka
- Maven / Gradle

---

# Advantages of This Architecture

- Centralized Routing
- Dynamic Service Discovery
- Scalable Microservices
- Load Balancing
- Reactive Non-Blocking Gateway
- Easy Service Management

---

# Run Order

Start services in this order:

```text
1. Eureka Server
2. Content Service
3. API Gateway
```

---

# Test Using CURL

```bash
curl http://localhost:8001/content/welcome
```

---

# Future Enhancements

- JWT Authentication
- Config Server
- Distributed Tracing
- Resilience4j Circuit Breaker
- Docker & Kubernetes Deployment
- Multiple Content Service Instances
- API Rate Limiting

---

# Author

Krishnamoorthy
