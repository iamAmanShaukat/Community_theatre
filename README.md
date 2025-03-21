

# **Greenwich Community Theatre (GCT) - Ticket Booking System**

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## **Overview**

The Greenwich Community Theatre (GCT) Ticket Booking System is a robust web application designed to manage event bookings, ticket sales, discounts, and user interactions. Built using **Spring Boot**, this system provides a scalable and secure platform for managing theatre events, seat reservations, payments, and email confirmations.

Key features include:
- User authentication and role-based access control.
- Event management with image uploads and show time scheduling.
- Dynamic discount calculations based on ticket types and user behavior.
- Real-time seat availability checks and temporary locking using Redis.
- Payment gateway integration with simulated payment processing.
- Asynchronous ticket delivery via email with QR codes and PDF attachments.
- Comprehensive logging, error handling, and monitoring.

This project is ideal for small to medium-sized theatres or event organizers looking to streamline their ticketing operations.

---

## **Table of Contents**

1. [Features](#features)
2. [Technologies Used](#technologies-used)
3. [Setup Instructions](#setup-instructions)
4. [API Documentation](#api-documentation)
5. [Project Structure](#project-structure)
6. [License](#license)

---

## **Features**

### **Authentication**
- **User Signup/Login**: Secure user registration and login using JWT tokens.
- **Role-Based Access Control**: Admin and user roles with restricted access to sensitive endpoints.

### **Event Management**
- **Add/Update/Delete Events**: Manage events with details like name, genre, start/end dates, duration, description, producer, director, and images.
- **Show Time Management**: Add, retrieve, and delete show times for events.

### **Ticketing**
- **Seat Availability Check**: Verify seat availability and temporarily lock seats during booking.
- **Book Tickets**: Process payments and generate tickets upon successful payment.
- **Retrieve Tickets**: Fetch ticket details by ticket number.

### **Discounts**
- **Dynamic Discount Calculation**: Calculate discounts based on ticket types, social club memberships, and last-hour/weekday specials.
- **Manage Discounts**: Create, retrieve, update, and delete discounts.

### **Payment Gateway**
- **Simulated Payment Processing**: Validate payment details and return a transaction ID upon success.

### **Email Notifications**
- **Confirmation Emails**: Send confirmation emails to users after successful ticket booking, including a PDF ticket with a QR code.

### **Reviews**
- **Submit Reviews**: Allow users to submit reviews for events, including ratings and comments.
- **Retrieve Reviews**: Fetch all reviews for a specific event.

---

## **Technologies Used**

- **Backend Framework**: Spring Boot
- **Database**: PostgreSQL (or any relational database supported by JPA)
- **Authentication**: JWT (JSON Web Tokens)
- **Image Processing**: Cloudinary, Thumbnailator
- **Seat Locking**: Redis
- **Email Service**: SendGrid
- **PDF Generation**: iText
- **QR Code Generation**: ZXing
- **Logging**: SLF4J
- **Testing**: JUnit, Mockito
- **Build Tool**: Maven

---

## **Setup Instructions**

### **Prerequisites**

1. **Java 17+**: Ensure you have Java installed on your machine.
2. **Maven**: Install Maven to build and run the project.
3. **PostgreSQL**: Set up a PostgreSQL database.
4. **Redis**: Install Redis for seat locking.
5. **Cloudinary**: Create a Cloudinary account for image uploads.
6. **SendGrid**: Create a SendGrid account for email notifications.

### **Steps to Run Locally**

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/gct-ticket-booking-system.git
   cd gct-ticket-booking-system
   ```

2. **Set Up Environment Variables**:
   Create a `.env` file in the root directory and add the following variables:
   ```env
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/gctdb
   SPRING_DATASOURCE_USERNAME=your_db_username
   SPRING_DATASOURCE_PASSWORD=your_db_password
   JWT_SECRET=your_jwt_secret_key
   CLOUDINARY_CLOUD_NAME=your_cloudinary_cloud_name
   CLOUDINARY_API_KEY=your_cloudinary_api_key
   CLOUDINARY_API_SECRET=your_cloudinary_api_secret
   SENDGRID_API_KEY=your_sendgrid_api_key
   REDIS_HOST=localhost
   REDIS_PORT=6379
   ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the APIs**:
   Once the server starts, the APIs will be accessible at `http://localhost:8080`.

---

## **API Documentation**

The project includes comprehensive API documentation using Swagger/OpenAPI. To access the API documentation:

1. Start the application.
2. Navigate to `http://localhost:8080/swagger-ui.html`.

### **Endpoints Overview**

| **Controller**       | **Endpoint**                         | **Description**                                   |
|-----------------------|--------------------------------------|--------------------------------------------------|
| AuthController        | `/api/v1/auth/signup`               | Register a new user                             |
| AuthController        | `/api/v1/auth/login`                | Authenticate and log in a user                  |
| BandController        | `/api/v1/bands/all`                 | Get all bands                                   |
| DiscountController    | `/api/v1/discounts/calculate`       | Calculate discounts for a booking              |
| EventController       | `/event/get-all-events`             | Get all events                                  |
| PaymentController     | `/api/v1/payments/process`          | Process a payment                               |
| SeatController        | `/seats/verify/{eventId}/{showId}`  | Verify seat availability                        |
| TicketController      | `/api/v1/tickets/{ticketNumber}`    | Get ticket details                              |
| ReviewController      | `/api/v1/reviews/save`              | Submit a review                                 |

---

## **Project Structure**

The project follows a clean architecture with the following structure:

```
src/
├── main
│   ├── java
│   │   └── project
│   │       └── community
│   │           └── theatre
│   │               ├── GreenwichCommunityTheatre.java
│   │               ├── config
│   │               ├── constant
│   │               ├── controller
│   │               ├── dto
│   │               ├── enums
│   │               ├── exception
│   │               ├── filter
│   │               ├── mapper
│   │               ├── model
│   │               ├── repository
│   │               ├── service
│   │               └── util
│   └── resources
│       └── application.properties
```

---

## **License**

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---
