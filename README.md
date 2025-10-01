# OnlineLibrary

A simple MVC Spring Boot application to manage books, users, and loans for a small online library. It uses Spring Web, Spring Data JPA, Validation, Thymeleaf for the UI, PostgreSQL as the database, Lombok to reduce boilerplate, and Flyway for database migrations.

## Features
- Manage Books: create, edit, delete, list all, list only available.
- Manage Users: create, edit, list.
- Manage Loans: create a new loan (book becomes LOANED) and return a book (book becomes AVAILABLE).
- Validations: all entity fields are non-null; loan withdrawalDate cannot be after returnDate.
- Database migrations with Flyway run automatically at startup.

## Tech stack
- Java 21
- Spring Boot 3
- Spring MVC, Spring Data JPA, Validation
- Thymeleaf (server-side rendering)
- PostgreSQL
- Flyway
- Lombok
- Tailwind CSS (via CDN) in templates

## Project structure (high level)
- `src/main/java/com/cerbon/onlinelibrary` — application code
  - `model` — entities: `Book`, `User`, `Loan`
  - `model/type` — enum: `BookStatus` (AVAILABLE, LOANED)
  - `repository` — Spring Data `JpaRepository` interfaces
  - `service` — services for books, users, and loans
  - `controller` — MVC controllers and routes
- `src/main/resources/templates` — Thymeleaf views (Books, Users, Loans, Home)
- `src/main/resources/db/migration` — Flyway migrations (V1..V3)
- `application.properties` — DB and app configuration
- `compose.yaml` — local PostgreSQL with Docker

## Prerequisites
- Java 21 (JDK)
- Docker and Docker Compose (for local PostgreSQL)
- Internet access (to load Tailwind CDN in templates)

Gradle Wrapper is included; you don't need a local Gradle install.

## Getting started

### 1) Start PostgreSQL with Docker Compose
From the project root:

```powershell
# Windows PowerShell
docker compose up -d
```

This starts a PostgreSQL container with the following defaults (see `compose.yaml`):
- POSTGRES_DB=mydatabase
- POSTGRES_USER=myuser
- POSTGRES_PASSWORD=secret
- Port 5432 exposed on localhost

The application is configured to use these defaults in `src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.datasource.username=myuser
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
```

You can override these with environment variables if needed, e.g. `SPRING_DATASOURCE_URL`, etc.

### 2) Run the application
Run with the Gradle wrapper:

```powershell
# Windows PowerShell
./gradlew.bat bootRun
```

Or build a jar and run it:

```powershell
./gradlew.bat clean build
java -jar .\build\libs\OnlineLibrary-0.0.1-SNAPSHOT.jar
```

On startup, Flyway will apply migrations (V1..V3) to create the tables.

### 3) Open the UI
Visit:
- http://localhost:8080/

Navigation links available in the header:
- Home: `/`
- Users: `/users`
- Books: `/books` (and `/books/available`)
- Active Loans: `/loans`

### 4) Using the app
- Books page: Add Book, view All vs Available, Edit, Delete.
- Users page: Add User, Edit.
- Active Loans: New Loan, Return Book.

Business rules:
- New books default to status AVAILABLE.
- Creating a loan sets the selected book status to LOANED.
- Returning a loan sets the book back to AVAILABLE.
- Loan `withdrawalDate` must be on or before `returnDate`.

## Troubleshooting
- Port 5432 already in use: stop other Postgres instances or change the mapped port in `compose.yaml` and update `application.properties` accordingly.
- Cannot connect to database: ensure the container is running (`docker ps`) and credentials/URL match.
- Flyway validate/migration errors: ensure the database is clean for first run. You may drop and recreate the `mydatabase` DB in your local Postgres if you changed migrations.
- Build issues with Lombok: make sure your IDE has Lombok annotation processing enabled. The Gradle build already includes the correct dependencies.

## Running tests
```powershell
./gradlew.bat test
```

## Environment variables (optional overrides)
- `SPRING_DATASOURCE_URL` (e.g., `jdbc:postgresql://localhost:5432/mydatabase`)
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SERVER_PORT` (default 8080)

## License
This project is provided as-is for demonstration/educational purposes.
