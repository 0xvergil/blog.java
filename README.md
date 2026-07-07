# 01Blog

A fullstack social blogging platform. Students document their learning journey, interact with others, and manage content.

---

## Stack

- **Backend**: Java 21, Spring Boot 3, Spring Security, JWT, PostgreSQL, Hibernate/JPA
- **Frontend**: Angular 19, Bootstrap 5
- **Auth**: JWT (stateless, role-based)

---

## Requirements

- Java 21+
- Maven
- Node.js + npm
- PostgreSQL

---

## Setup

### Database

```sql
CREATE DATABASE appdb;
```

### Backend

```bash
# Configure database credentials in:
backend/src/main/resources/application.properties

# Run
mvn -f backend/pom.xml spring-boot:run
```

Runs on `http://localhost:8080`

### Frontend

```bash
cd frontend
npm install
ng serve
```

Runs on `http://localhost:4200`

---

## application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/appdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
jwt.secret=your-secret-key-min-32-chars
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

---

## Features

- Register / Login (JWT)
- Create, edit, delete posts with media (image/video)
- Like and comment on posts
- Subscribe to users — feed shows subscribed users' posts
- Notifications on new posts from subscribed users
- Report users
- Admin panel — manage users, posts, reports
- Role-based access (USER / ADMIN)

---

## Roles

| Role  | Access |
|-------|--------|
| USER  | Full platform access |
| ADMIN | + Admin panel, delete any post/user |

To promote a user to admin:
```sql
UPDATE users SET role = 1 WHERE username = 'yourusername';
```

---

## Media

Uploaded files are stored in `backend/uploads/` (excluded from git).