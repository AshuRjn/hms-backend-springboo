🏨<h1> HMS - Hotel Management System (Spring Boot)</h1>

📌 <h2>Overview</h2>

HMS (Hotel Management System) is a scalable, microservices-based backend solution designed to streamline hotel operations. Built with Spring Boot, it supports property management, booking, payments, and third-party integrations for notifications and authentication. The system enables seamless hotel-booking functionality similar to Airbnb.

🚀 <h2>Features</h2>

✅ User Management & Authentication (Role-Based Access Control)
<br>
✅ Property & Room Management (CRUD operations for hotels & listings)
<br>
✅ Booking & Reservation System (Search, book, and manage stays)
<br>
✅ Payment Gateway Integration
<br>
✅ Real-time Notifications via SMS, Email, WhatsApp
<br>
✅ OTP Verification System for secure logins
<br>
✅ AWS S3 Integration for image storage
<br>
✅ Microservices Architecture (scalability & modular design)
<br>
✅ Dockerized Deployment & CI/CD Pipelines

🛠️<h2> Tech Stack </h2>

Backend: Java, Spring Boot, Spring Security, Spring Data JPA

Database: MySQL, Hibernate

Messaging & Streaming: Apache Kafka

Cloud Services: AWS S3 (for images), AWS SES (for emails), AWS SNS (for SMS)

Containerization: Docker

CI/CD: Jenkins, GitHub Actions

Testing: JUnit, Mockito

🔧 <h2>Installation & Setup</h2>

Clone the repository:

git clone https://github.com/AshuRjn/hms-backend-springboo.git
cd hms-backend-springboo

Configure application.properties with database, AWS, and third-party service credentials.

Build & run the application:

mvn clean install
mvn spring-boot:run

The application runs at: http://localhost:8080

💼<h2> API Endpoints </h2>

HMS exposes RESTful APIs for managing hotels, bookings, users, and payments.

Uses standard HTTP methods (GET, POST, PUT, DELETE).

💡 <h2>Contribution Guidelines</h2>

Fork the repository

Create a new branch

Commit your changes with meaningful messages

Submit a pull request

📩 <h2>Contact</h2>

For queries & collaborations, reach out at ashutech31@gmail.com
