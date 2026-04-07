# springmvcdemo

Пример веб-приложения на Spring Boot с MVC, Kotlin и Gradle.

## Описание

Приложение для отображения новостных заголовков через внешний API ([NewsAPI](https://newsapi.org/)). Пользователь может перейти на страницу новостей, где приложение через Feign-клиент загружает актуальные статьи из указанных источников.

### Возможности

- Отображение приветственной страницы
- Загрузка и отображение списка новостей через NewsAPI
- Интеграция с внешним REST API через Spring Cloud OpenFeign
- Шаблонизация через Thymeleaf с Bootstrap-стилизацией
- Автоматическая перезагрузка при разработке (Spring Boot DevTools)

## Стек технологий

| Технология | Версия |
|------------|--------|
| Java | 21 |
| Kotlin | 2.1.10 |
| Spring Boot | 3.5.5 |
| Spring Cloud | 2025.0.0 |
| Gradle | 8.12 |
| Thymeleaf | — |
| Bootstrap | 5.1.3 |
| Spring Cloud OpenFeign | — |
| Jackson (Kotlin) | — |
| Webjars | — |

### Тестирование

| Библиотека | Версия |
|------------|--------|
| JUnit 5 | — |
| Spring Boot Test | — |
| MockK | 1.13.13 |

## Запуск

```bash
./gradlew bootRun
```

После запуска приложение доступно по адресу: [http://localhost:8080](http://localhost:8080)

## Структура проекта

```
src/main/kotlin/ru/yzuykov/springmvcdemo/
├── controller/        # Контроллеры (NewsController)
├── service/           # Сервисный слой
│   ├── api/           # Интерфейсы сервисов
│   └── impl/          # Реализации сервисов
├── client/            # Feign-клиенты для внешних API
├── config/            # Конфигурация приложения
└── model/             # DTO и модели данных
```

## Конфигурация

Настройки NewsAPI указываются в `application.yaml`:

```yaml
news:
  url: "https://newsapi.org/v2"
  apiKey: "your-api-key"
  sources: "google-news-ru"
```

## Тесты

```bash
./gradlew test
```

Отчёт покрытия JaCoCo: `build/reports/jacoco/test/html/index.html`
