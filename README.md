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

---

## Services

### 1) Eureka Service Registry

This acts as the central registry where services register themselves.

Dashboard:

```bash
http://localhost:8761
```

---

### 2) API Gateway

The gateway receives incoming traffic and routes it to registered services using Spring Cloud Gateway.

```bash
http://localhost:8001
```
---

### 3) Auth Service

This service is used to login and verify token.

```bash
http://localhost:8001/auth/
```

### 4) Content Service

This service is a simple REST service registered in Eureka.

```bash
http://localhost:8001/content/
```

---

## Run the project

Start in this order:

1. Eureka Service Registry
2. Content Service
3. Auth Service
4. API Gateway

## Technology stack

- Java 17+
- Spring Boot
- Spring Cloud Starter Gateway Server Webmvc
- Netflix Eureka
- Gradle

---

## Why this architecture is useful

- Centralized routing
- Dynamic service discovery
- Better separation of concerns
- Load balancing support
- Simple microservice learning model

---

## Future improvements

- JWT authentication
- Spring Cloud Config Server
- Distributed tracing
- Resilience4j circuit breaker
- Docker and Kubernetes deployment
- API gateway rate limiting

---

## Author

Krishnamoorthy
