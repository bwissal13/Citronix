# **Citronix**

## **Table of Contents**
1. [Introduction](#introduction)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Architecture Overview](#architecture-overview)
5. [Setup Instructions](#setup-instructions)
6. [Usage](#usage)
7. [Constraints](#constraints)
8. [Contributing](#contributing)


---

## **Introduction**
**Citronix** is a Java-based farm management application designed to help farmers efficiently manage lemon production, harvesting, and sales. The application provides tools for overseeing farms, fields, trees, harvests, and sales while ensuring compliance with agricultural business rules. By automating productivity tracking and revenue calculations, Citronix optimizes farm management for maximum efficiency.

---

## **Features**
### **1. Farm Management**
- Add, update, delete, and view farm details (name, location, size, creation date).
- Perform multi-criteria searches for farms using the `CriteriaBuilder`.

### **2. Field Management**
- Associate fields with farms and define field sizes.
- Validate field constraints:
  - Minimum size: **0.1 hectares**.
  - Maximum size: **50% of the farm area**.
  - Limit of **10 fields per farm**.

### **3. Tree Management**
- Track tree data, including planting date, age, and associated field.
- Automatically calculate tree ages and productivity based on age:
  - Young trees (<3 years): **2.5 kg/season**.
  - Mature trees (3–10 years): **12 kg/season**.
  - Old trees (>10 years): **20 kg/season**.
- Enforce the planting season (March to May).

### **4. Harvest Management**
- Record harvests for each season (winter, spring, summer, autumn).
- Restrict to one harvest per field per season.
- Track harvest details (date and total quantity).

### **5. Harvest Details**
- Associate trees with specific harvests.
- Record the quantity harvested per tree.

### **6. Sales Management**
- Track sales details (date, unit price, client, and associated harvest).
- Calculate revenue:  
  **Revenue = Quantity × Unit Price**.

---

## **Technologies Used**
- **Frameworks**: Spring Boot
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Testing**: JUnit, Mockito
- **Libraries**:
  - **Lombok**: Simplifies entity management.
  - **MapStruct**: Converts entities to DTOs.
  - **Swagger**: Documents RESTful APIs.
- **Development Tools**:
  - IntelliJ IDEA
  - Postman
  - Git and GitHub

---

## **Architecture Overview**
Citronix follows a **layered architecture** to ensure scalability and maintainability:
1. **Controller Layer**: Exposes RESTful APIs.
2. **Service Layer**: Implements business logic, such as validating constraints and calculating productivity.
3. **Repository Layer**: Manages database interactions using Spring Data JPA.
4. **Entity Layer**: Maps database tables to Java objects.
5. **DTO Layer**: Optimizes data transfer between the client and application.
6. **Validation**: Ensures data integrity using Spring Bean Validation.

Additional Highlights:
- Centralized exception handling for consistent error responses.
- Database versioning with **Liquibase** for controlled schema changes.

---

## **Setup Instructions**
### Prerequisites
- Java 17 or later
- Maven
- PostgreSQL
- Git

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/bwissal13/citronix.git
   ```
2. Navigate to the project directory:
   ```bash
   cd citronix
   ```
3. Configure the PostgreSQL database:
   - Create a database named `citronix_db`.
   - Update the database credentials in `application.properties`.
4. Build the project:
   ```bash
   mvn clean install
   ```
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```
6. Access the application:
   - Swagger API documentation: [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)

---

## **Usage**
### Key Endpoints
- **Farm Management**:
  - `POST /farms`: Create a new farm.
  - `GET /farms`: Retrieve all farms.
- **Field Management**:
  - `POST /fields`: Add a field to a farm.
  - `GET /fields`: List fields of a farm.
- **Tree Management**:
  - `POST /trees`: Add trees to a field.
  - `GET /trees`: Retrieve tree details.
- **Harvest Management**:
  - `POST /harvests`: Record a harvest.
  - `GET /harvests`: View all harvests.
- **Sales Management**:
  - `POST /sales`: Record a sale.
  - `GET /sales`: Retrieve sales data.

For detailed API specifications, refer to the Swagger documentation.

---

## **Constraints**
- **Field Area**:
  - Minimum: **0.1 hectares**.
  - Maximum: **50% of farm area**.
  - Limit: **10 fields per farm**.
- **Tree Productivity**:
  - Maximum productive age: **20 years**.
- **Tree Planting Season**: March to May.
- **Harvest Rules**:
  - One harvest per season per field.
  - A tree cannot be included in multiple harvests in the same season.

---

## **Contributing**
We welcome contributions to Citronix!  
Please follow these steps:
1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes and push to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```
4. Submit a pull request.

---

