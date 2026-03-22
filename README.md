# CRM Backend System (Spring Boot)

#Description

A complete Customer Relationship Management (CRM) backend system built using Spring Boot.
This project includes secure authentication, role-based authorization, customer management, dashboard analytics, Excel export, and email notifications.

#Features
- User Registration & Login (JWT Authentication)
- Role-Based Access Control (ADMIN, EMPLOYEE)
- Customer Management (CRUD)
- Customer Assignment to Employees
- Pagination & Search
- Dashboard APIs (Analytics)
- Graph Data API for charts
- Export Customer Data to Excel
- Email Notifications (on customer assignment)

#Tech Stack
Backend: Java, Spring Boot
Security: Spring Security, JWT
Database: MySQL
ORM: Spring Data JPA (Hibernate)
Other Tools: Apache POI (Excel Export), Spring Mail

#Authentication
JWT-based authentication is used.
After login, a token is generated which must be passed in headers:
Authorization: Bearer <token>

#API Endpoints
Auth APIs:
POST /api/users/register
POST /api/users/login

Customer APIs:
POST /api/customer/create
GET /api/customer/my-customers
GET /api/customer/all (Admin)
PUT /api/customer/assign

Dashboard APIs:
GET /api/customer/dashboard/total-customers
GET /api/customer/dashboard/total-employees
GET /api/customer/dashboard/chart

Export API:
GET /api/customer/export

# Email Feature
Email is sent when a customer is assigned to an employee using Spring Boot Mail.

#Learning Outcome
- Implemented JWT Authentication & Authorization
- Designed layered architecture (Controller-Service-Repository)
- Built real-world APIs
- Integrated external services (Email)
- Generated Excel files dynamically

