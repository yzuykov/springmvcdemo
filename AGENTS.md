# AGENTS.md

## Commands

```bash
./gradlew bootRun      # Run app at http://localhost:8080
./gradlew test        # Run tests
./gradlew build      # Build JAR
./gradlew jacocoTestReport  # Generate coverage report
```

## Tech Stack

- Java 21 (JVM toolchain)
- Kotlin 2.1.10
- Spring Boot 4.0.1
- Spring Framework 7.0.2
- `@HttpExchange` (declarative HTTP client)

## Architecture

```
src/main/kotlin/ru/yzuykov/springmvcdemo/
├── controller/   # NewsController: `/`, `/news`
├── service/{api,impl}/  # NewsService interface + impl
├── client/       # NewsHttpClient (@HttpExchange interface)
├── config/       # NewsProperties
└── model/        # DTOs
```

## Configuration

`src/main/resources/application.yaml`:

```yaml
news:
  apiKey: ${NEWS_API_KEY:}
```

Set `NEWS_API_KEY` env var for API access.

## HTTP Client

Uses `@HttpExchange` annotation from Spring Framework 7 (replaces Feign):

```kotlin
@HttpExchange("https://newsapi.org")
interface NewsHttpClient {
    @GetExchange("/v2/top-headlines")
    fun getTopHeadLines(@RequestParam sources: String, @RequestHeader("X-Api-Key") apiKey: String): NewsResponse

    @GetExchange("/v2/top-headlines/sources")
    fun getSources(@RequestParam language: String, @RequestHeader("X-Api-Key") apiKey: String): SourcesResponse
}
```

## Testing

- Unit tests in `src/test/kotlin/`
- JUnit 5 + MockK
- JaCoCo coverage: `build/reports/jacoco/test/html/index.html`