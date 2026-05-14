<img width="1906" height="455" alt="image" src="https://github.com/user-attachments/assets/60937c95-dba3-434d-b2d3-17600796b712" /># 📚 Virtual Bookstore
A full-stack **Spring Boot learning project** that simulates an online bookstore where users can browse books, add items to cart, place orders, and manage authentication using JWT.

This project was built to practice:
* Spring Boot REST APIs
* Spring Security + JWT Authentication
* Spring Data JPA
* PostgreSQL Integration
* Frontend integration with HTML, CSS, and JavaScript
* Order and cart management logic
------------------------------------------------------
# 🚀 Features

## 👤 Authentication
* User Signup
* User Login
* JWT-based Authentication
* Protected API endpoints using Spring Security

## 📚 Book Management
* View all books
* View book details
* View title & author projections
* Stock management

## 🛒 Cart & Orders
* Add books to cart
* Checkout multiple books
* Place orders
* View previous orders

## 🎨 Frontend Pages
* Home Page
* Login Page
* Signup Page
* Cart Page
* Orders Page
* Book Details Page

---------------------------------------------
# 🛠️ Tech Stack
## Backend
* Java 17
* Spring Boot 3
* Spring Security
* Spring Data JPA
* JWT Authentication
* Maven

## Database
* PostgreSQL

## Frontend
* HTML
* CSS
* JavaScript
-----------------------------------------
# 🔐 Authentication Flow
1. User signs up
2. User logs in
3. JWT token is generated
4. Token is sent in Authorization header
5. Protected endpoints validate the token

Example:
```http
Authorization: Bearer your_jwt_token
```

-----------------------------------------

# 📡 API Endpoints

## User APIs

| Method | Endpoint        | Description       |
| ------ | --------------- | ----------------- |
| POST   | `/users/signup` | Register new user |
| POST   | `/users/login`  | Login user        |

## Book APIs

| Method | Endpoint              | Description               |
| ------ | --------------------- | ------------------------- |
| GET    | `/books`              | Get all books             |
| GET    | `/books/{id}`         | Get book by ID            |
| GET    | `/books/title-author` | Get book title and author |
| PUT    | `/books/buy/{id}`     | Buy single book           |
| POST   | `/books/checkout`     | Checkout cart             |
| GET    | `/books/orders`       | Get user orders           |
-----------------------------------------

# ⚙️ Setup & Installation

## 1️⃣ Clone the Repository
```bash
git clone https://github.com/your-username/VirtualBookshop.git
cd VirtualBookshop
```

## 2️⃣ Configure PostgreSQL
Create a PostgreSQL database.
Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## 3️⃣ Run the Application
Using Maven:
```bash
mvn spring-boot:run
```
Or:
```bash
./mvnw spring-boot:run
```
Application will run at:
```bash
http://localhost:8080
```
------------------------------------
# 📖 Learning Goals
This project helped me understand:
* REST API development
* Layered architecture in Spring Boot
* JWT authentication workflow
* Database relationships using JPA
* Frontend and backend integration
* Exception handling
* Repository pattern
* Secure endpoint implementation

-------------------------------

# 🔮 Future Improvements
* Admin dashboard
* Payment gateway integration
* Book search & filters
* Pagination
* Docker support
* Unit & integration testing
* Swagger/OpenAPI documentation
* Responsive UI improvements
-----------------------------------

# 🙌 Acknowledgements
This is a personal learning project created to improve backend development skills using Spring Boot.
------------------------------------------------------

# ⭐ If You Like This Project
Give this repository a star and feel free to fork it for learning purposes.
