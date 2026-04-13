# QWEN.md — Project Context

## Project Overview

**springmvcdemo** is a Spring Boot web application built with Kotlin, Gradle, and the MVC pattern. The application displays news headlines by integrating with the external [NewsAPI](https://newsapi.org/) service. Users can view a welcome page and navigate to a news page where articles are loaded via a Feign client from specified sources.

### Architecture

The project follows a layered architecture:

```
src/main/kotlin/ru/yzuykov/springmvcdemo/
├── controller/        # MVC Controllers (NewsController)
├── service/           # Service layer
│   ├── api/           # Service interfaces
│   └── impl/          # Service implementations
├── client/            # Feign clients for external API integration
├── config/            # Application configuration classes
└── model/             # DTOs and data models
```

### Tech Stack

| Technology | Version |
|------------|---------|
| Java | 21 (JVM toolchain) |
| Kotlin | 2.1.10 |
| Spring Boot | 3.5.5 |
| Spring Cloud | 2025.0.0 (BOM) |
| Gradle | 8.12 |
| Thymeleaf | — |
| Bootstrap | 5.3.3 |
| Spring Cloud OpenFeign | — |
| Webjars | 0.52 |

### Testing

| Library | Version |
|---------|---------|
| JUnit 5 | — |
| Spring Boot Test | — |
| MockK | 1.14.4 |
| JaCoCo | — (code coverage reporting) |

## Building and Running

### Prerequisites

- Java 21 JDK
- Gradle 8.12+ (or use the provided `gradlew` wrapper)

### Commands

```bash
# Run the application
./gradlew bootRun

# Run tests
./gradlew test

# Build the project
./gradlew build

# Generate JaCoCo test coverage report
./gradlew jacocoTestReport
```

After running, the application is available at: http://localhost:8080

## Configuration

NewsAPI settings are configured in `src/main/resources/application.yaml`:

```yaml
news:
  url: "https://newsapi.org/v2"
  apiKey: "your-api-key"
  sources: "google-news-ru"
```

These properties are bound to `NewsProperties` (enabled via `@EnableConfigurationProperties`).

## Key Components

- **SpringmvcdemoApplication** — Main entry point; enables Feign clients and configuration properties
- **NewsController** — Handles `/` (welcome) and `/news` (news listing) routes
- **NewsService / NewsServiceImpl** — Service layer for retrieving articles
- **NewsClient** — Feign client interface for calling the NewsAPI `/top-headlines` endpoint
- **NewsResponse / ArticleDto** — DTOs for API response deserialization
- **NewsProperties** — Configuration properties class bound to the `news.*` YAML namespace

## Templates & Static Assets

- Thymeleaf templates are located in `src/main/resources/templates/`
- Bootstrap is included via Webjars (`org.webjars.npm:bootstrap:5.3.3`)
- `webjars-locator` enables simplified asset references

## Development Conventions

- **Kotlin coding style** — Follow standard Kotlin conventions; property injection uses constructor `@Autowired`
- **Layered architecture** — Controllers → Services → Clients; interfaces are in `api/`, implementations in `impl/`
- **Testing** — Unit tests use MockK for mocking; tests exist for both `NewsService` and `NewsController`
- **Code coverage** — JaCoCo is configured with XML and HTML reports

## Git & Project Notes

- Branch: `master` (tracks `origin/master`)
- Group/Version: `ru.yzuykov:springmvcdemo:1.0.0-SNAPSHOT`
- The project uses Spring Cloud BOM for dependency management rather than hardcoding OpenFeign versions
