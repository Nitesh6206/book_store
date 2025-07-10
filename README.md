
markdown
Copy
Edit
# 📚 Online Book Store (Spring Boot + MySQL)

This is a full-featured **Online Book Store** backend developed using **Spring Boot**, **Spring Security**, **JWT**, and **MySQL**. It allows users to register, authenticate, browse books, make purchases, and manage their purchase history.

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
| Persistence | Spring Data JPA, Hibernate, MySQL |
| Build Tool  | Maven                            |
| Testing     | Postman (for API testing)        |

---

## 📁 Project Structure

book_store/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com.niteshkr.online_book_store/
│ │ │ ├── controller/
│ │ │ ├── entity/
│ │ │ ├── repository/
│ │ │ ├── service/
│ │ │ ├── dto/
│ │ │ ├── security/
│ │ │ └── exception/
│ │ └── resources/
│ │ └── application.properties
├── pom.xml
└── README.md

yaml
Copy
Edit

---

## 🧑‍💻 How to Set Up Locally

### ✅ 1. Clone the Repository

```bash
git clone https://github.com/Nitesh6206/book_store.git
cd book_store
✅ 2. Configure MySQL Database
Open your MySQL CLI or Workbench and create a database:

sql
Copy
Edit
CREATE DATABASE book_store;
✅ 3. Update application.properties
Edit the file:
src/main/resources/application.properties

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/book_store
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT config
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
✅ The application will start on:
http://localhost:8080

🧪 API Endpoints
Endpoint	Method	Access
/api/auth/register	POST	Public
/api/auth/login	POST	Public
/api/books	GET	Authenticated
/api/books	POST	Admin Only
/api/purchase	POST	Customer
/api/purchase/history	GET	Customer

Use Postman or any REST client and pass the JWT token in headers:

makefile
Copy
Edit
Authorization: Bearer <your-token>
🔐 Role Management
Role	Capabilities
ROLE_ADMIN	Add, update, delete books
ROLE_CUSTOMER	Purchase and view history

📦 Dependencies
Some key dependencies in pom.xml:

xml
Copy
Edit
<dependency>spring-boot-starter-security</dependency>
<dependency>spring-boot-starter-data-jpa</dependency>
<dependency>spring-boot-starter-web</dependency>
<dependency>io.jsonwebtoken:jjwt</dependency>
<dependency>mysql:mysql-connector-java</dependency>
🙋‍♂️ Author
Nitesh Kumar
📧 Email
🔗 LinkedIn
💼 Portfolio

🤝 Contributing
Pull requests are welcome! If you’d like to contribute, fork the repo and submit a PR.
