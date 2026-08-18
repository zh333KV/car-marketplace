# Car marketplace 🚗

A web application for buying and selling cars. Users can create vehicle listings with photos and specifications, browse available offers, search by parameters, and manage their own listings.

> **Pet project** built with Java and Spring Boot to practice backend development, authentication, JPA/Hibernate, server-side rendering, PostgreSQL, and Docker.

---

## ✨ Features

* User registration and authentication
* User profiles
* Create car listings
* Edit and delete your own listings
* Upload multiple car images
* View detailed vehicle information
* Search listings by query and city
* Seller profiles with their active listings
* Server-side form validation
* Access control for protected actions
* PostgreSQL persistence
* Dockerized application and database
* Persistent storage for uploaded images through Docker volumes

---

## 🛠️ Tech Stack

### Backend

* **Java 17**
* **Spring Boot 4**
* **Spring MVC**
* **Spring Security**
* **Spring Data JPA**
* **Hibernate**
* **Bean Validation**
* **Lombok**

### Frontend

* **Thymeleaf**
* HTML / CSS
* Thymeleaf Spring Security integration

### Database

* **PostgreSQL 16**

### Build & Infrastructure

* **Maven**
* **Docker Compose**

The project uses Spring Boot 4.0.6 and Java 17.

---

## 🏗️ Architecture

The application follows a layered Spring architecture:

```text
src/main/java/core/
├── config/
├── controller/
├── dto/
├── entity/
├── repository/
├── security/
├── service/
│   └── impl/
└── AutoSalesApplication.java
```

---

## 🔐 Authentication & Authorization

Authentication is implemented with Spring Security.

Available authentication flows include:

* Registration
* Login
* Authenticated user profiles
* Protected listing management
* Ownership checks when editing or deleting advertisements

Users can only modify their own listings.

---

## 🚘 Listings

Each advertisement can contain vehicle information and associated images.

Main listing operations:

```text
GET  /                    Home page
GET  /ads                 All listings
GET  /ads/{id}            Listing details
GET  /ads/create          Create listing
POST /ads/create          Save listing
GET  /ads/edit/{id}       Edit listing
POST /ads/edit/{id}       Update listing
POST /ads/delete/{id}     Delete listing
GET  /ads/search          Search listings
```

---

## 🐳 Running with Docker

### Requirements

Make sure you have installed:

* Docker

### 1. Clone the repository

```bash
git clone https://github.com/zh333KV/car-marketplace.git
cd car-marketplace
```

### 2. Start the application

```bash
docker compose up --build
```

Docker Compose starts:

```text
PostgreSQL
    │
    ▼
Spring Boot application
    │
    ▼
http://localhost:8080
```

The Compose configuration uses PostgreSQL 16, waits for the database healthcheck, starts the Spring Boot application, and exposes it on port `8080`.

---

## 📁 Why there is no .env file

This project uses a fixed local PostgreSQL configuration for simplicity and reproducibility. The credentials are intentionally non-sensitive (postgres / password) and are used only for the local Docker environment, so an additional .env file is unnecessary for this project.

---

## 📸 Screenshots

### Registration and Login

<img width="321" height="320" alt="image" src="https://github.com/user-attachments/assets/d97f6c6d-3c8c-40d1-a81c-5811cb8973d3" />   <img width="263" height="195" alt="image" src="https://github.com/user-attachments/assets/bb7e6830-72e3-464b-8056-777a04dfc0fc" />

### Home page

<img width="1299" height="929" alt="image" src="https://github.com/user-attachments/assets/5eb9cafd-7ac3-4726-885d-8e82e993b5c8" />

### Car listings

<img width="1269" height="731" alt="image" src="https://github.com/user-attachments/assets/ee13b6a7-c5d6-4e50-9948-eaee59321df4" />

### Car details

<img width="1274" height="847" alt="image" src="https://github.com/user-attachments/assets/7762aeb4-5610-4392-a5e4-8533abfb3f59" />

### Create / edit listing

<img width="1278" height="835" alt="image" src="https://github.com/user-attachments/assets/972eb9af-5d61-4829-bf0f-3e15ac99bc61" />

### User profile

<img width="1275" height="924" alt="image" src="https://github.com/user-attachments/assets/5fb7f0d0-f426-4507-be71-f409390df06f" />

---

## 📄 License

This project is a personal educational / portfolio project.
