# 📚 Online Book Store (Spring Boot + PostgreSQL)

This is a full-featured **Online Book Store** backend developed using **Spring Boot**, **Spring Security**, **JWT**, and **PostgreSQL**. It allows users to register, authenticate, browse books, make purchases, and manage their purchase history.

---

## 🚀 Features

- ✅ User Registration & Login (JWT-based authentication)
- ✅ Role-based access control (Admin & Customer)
- ✅ Add, update, delete books (Admin only)
- ✅ Purchase books (Customer)
- ✅ View purchase history
- ✅ RESTful API design
- ✅ Global exception handling

---

## 🛠️ Tech Stack

| Layer       | Technology                      |
|-------------|----------------------------------|
| Backend     | Java 17, Spring Boot             |
| Security    | Spring Security, JWT             |
| Persistence | Spring Data JPA, Hibernate, PostgreSQL |
| Build Tool  | Maven                            |
| Testing     | Postman (for API testing)        |

---

## 📁 Project Structure

<img width="341" alt="Screenshot 2025-07-10 at 2 58 04 PM" src="https://github.com/user-attachments/assets/c4719acb-ee5e-4421-8f0f-e7481e29da6f" />


yaml
Copy
Edit

---

## 🧑‍💻 How to Set Up Locally

### ✅ 1. Clone the Repository

```bash
git clone https://github.com/Nitesh6206/book_store.git
cd book_store
✅ 2. Configure PostgreSQL Database
Create a database in PostgreSQL:

sql
Copy
Edit
CREATE DATABASE book_store;
✅ 3. Update application.properties
Edit this file:
src/main/resources/application.properties

properties
Copy
Edit
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/book_store
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT Configuration
jwt.secret=your_jwt_secret
jwt.expiration=3600000
✅ 4. Build the Project
bash
Copy
Edit
mvn clean install
✅ 5. Run the Application
bash
Copy
Edit
mvn spring-boot:run
📍 The backend will run at:
http://localhost:8080

🧪 API Endpoints
Endpoint	Method	Access
/api/auth/register	POST	Public
/api/auth/login	POST	Public
/api/books	GET	Authenticated
/api/books	POST	SELLER Only
/api/purchase	POST	Customer
/api/purchase/history	GET	Customer

Use Postman or any REST client and pass the JWT token in headers:

makefile
Copy
Edit
Authorization: Bearer <your-token>
🔐 Role Management
Role	Capabilities
ROLE_SELLER	Add, update, delete books
ROLE_CUSTOMER	Purchase and view history

📦 Dependencies
Key dependencies in pom.xml:

xml
Copy
Edit
<dependency>spring-boot-starter-security</dependency>
<dependency>spring-boot-starter-data-jpa</dependency>
<dependency>spring-boot-starter-web</dependency>
<dependency>io.jsonwebtoken:jjwt</dependency>
<dependency>org.postgresql:postgresql</dependency>
🙋‍♂️ Author
Nitesh Kumar
📧 niteshsingh6206@gmial.com
🔗 https://www.linkedin.com/in/nitesh-kumar-67970125b/

🤝 Contributing
Pull requests are welcome! If you’d like to contribute, fork the repo and submit a PR.

