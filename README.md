# Fitness App Backend

This repository contains the backend API for the Fitness App, built using Java and Spring Boot.

---

### Prerequisites

Make sure you have the following installed before you start:

- **Java JDK 17**  
  
- **Maven** (for building the project)  
  
- **PostgreSQL** (or your preferred database)  
  Make sure to set up the database and configure the connection in `application.properties`

- An SMTP email account (e.g., Gmail) for sending emails
---

### Getting Started

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/fitness_app_backend.git
   cd fitness_app_backend

2. Install dependencies

- Run the following command to build project:
    ```bash
    ./mvnw clean install

3. Configure the local env variables in application.yml
   ```bash
   spring:
    datasource:
    url: YOUR_DATABASE_URL
    username: YOUR_DATABASE_USERNAME
    password: YOUR_DATABASE_PASSWORD
    
    mail:
    username: YOUR_EMAIL_ADDRESS
    password: YOUR_EMAIL_APP_PASSWORD
    
    security:
    jwt:
    secret-key: YOUR_JWT_SECRET_KEY

Replace the placeholders with your credentials

4. Run the application

### Front-end Application
- The front-end React Native app, including screenshots and demos, is available here: https://github.com/your-username/fitness-app-frontend
