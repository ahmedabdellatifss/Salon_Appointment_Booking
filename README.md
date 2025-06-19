# 💇‍♀️ Salon Appointment Booking - Microservices Architecture

A modern **Salon Appointment Booking System** built using **Spring Boot microservices**, **RabbitMQ**, **Keycloak** for authentication, **MySQL** for persistence, and a **React.js frontend**. Docker is used for containerization and deployment.

---

## 🧰 Tech Stack

| Layer               | Technology                |
|--------------------|---------------------------|
| Backend Framework  | Spring Boot 3.x           |
| Authentication     | Keycloak + JWT            |
| Messaging Queue    | RabbitMQ                  |
| API Gateway        | Spring Cloud Gateway      |
| Service Discovery  | Spring Cloud Eureka       |
| Database           | MySQL                     |
| Frontend           | React                     |
| Containerization   | Docker                    |

---

## 🧱 Microservices Overview

| Service                 | Description                           |
|-------------------------|---------------------------------------|
| `api-gateway`           | Central routing gateway               |
| `auth-service`          | Manages user login/registration (Keycloak) |
| `appointment-service`   | Handles appointment booking & scheduling |
| `stylist-service`       | Manages stylists and their availability |
| `notification-service`  | Sends booking notifications via RabbitMQ |
| `discovery-server`      | Eureka registry for service discovery |
| `frontend-app`          | React app with Formik forms for user interaction |

---

## 🧾 Features

- 🔐 Secure authentication with Keycloak and JWT
- 📅 Book appointments with available stylists
- 📣 Notifications sent via RabbitMQ
- 📂 Microservices organized for modular development
- ☁️ Docker Compose support for local orchestration

---

## 📁 Folder Structure


