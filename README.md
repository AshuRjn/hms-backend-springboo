🏨 HMS - Hotel Management System (Spring Boot)

📌 Overview

HMS (Hotel Management System) is a scalable, microservices-based backend solution designed to streamline hotel operations. Built with Spring Boot, it supports property management, booking, payments, and third-party integrations for notifications and authentication. The system enables seamless hotel-booking functionality similar to Airbnb.

🚀 Features

✅ User Management & Authentication (Role-Based Access Control)✅ Property & Room Management (CRUD operations for hotels & listings)✅ Booking & Reservation System (Search, book, and manage stays)✅ Payment Gateway Integration✅ Real-time Notifications via SMS, Email, WhatsApp✅ OTP Verification System for secure logins✅ AWS S3 Integration for image storage✅ Microservices Architecture (scalability & modular design)✅ Dockerized Deployment & CI/CD Pipelines

🛠️ Tech Stack

Backend: Java, Spring Boot, Spring Security, Spring Data JPA

Database: MySQL, Hibernate

Messaging & Streaming: Apache Kafka

Cloud Services: AWS S3 (for images), AWS SES (for emails), AWS SNS (for SMS)

Containerization: Docker

CI/CD: Jenkins, GitHub Actions

Testing: JUnit, Mockito

🔧 Installation & Setup

Clone the repository:

git clone https://github.com/AshuRjn/hms-backend-springboo.git
cd hms-backend-springboo

Configure application.properties with database, AWS, and third-party service credentials.

Build & run the application:

mvn clean install
mvn spring-boot:run

The application runs at: http://localhost:8080

💼 API Endpoints

HMS exposes RESTful APIs for managing hotels, bookings, users, and payments.

Uses standard HTTP methods (GET, POST, PUT, DELETE).

💡 Contribution Guidelines

Fork the repository

Create a new branch

Commit your changes with meaningful messages

Submit a pull request

📩 Contact

For queries & collaborations, reach out at ashutech31@gmail.com
