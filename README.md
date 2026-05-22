# springmvcdemo

Пример веб-приложения на Spring Boot с MVC, Kotlin и Gradle.

## Описание

Приложение для отображения новостных заголовков через внешний API ([NewsAPI](https://newsapi.org/)). На странице новостей через декларативный HTTP-клиент (`@HttpExchange`) загружаются доступные источники и актуальные статьи.

### Возможности

- Приветственная страница
- Загрузка и отображение новостей через NewsAPI
- Динамическая загрузка списка источников (по языку)
- Интеграция с внешним REST API через `@HttpExchange` (Spring Framework 7)
- Шаблонизация через Thymeleaf с кастомными CSS-стилями
- Дизайн-токены, CSS Grid, адаптивная вёрстка
- Автоматическая перезагрузка при разработке (Spring Boot DevTools)

## Стек технологий

| Технология | Версия |
|------------|--------|
| Java | 21 |
| Kotlin | 2.1.10 |
| Spring Boot | 4.0.1 |
| Spring Framework | 7.0.2 |
| Gradle | 8.12 |
| Thymeleaf | — |
| Jackson (Kotlin) | — |

### Тестирование

| Библиотека | Версия |
|------------|--------|
| JUnit 5 | — |
| MockK | 1.13.13 |

## Запуск

```bash
NEWS_API_KEY=your-api-key ./gradlew bootRun
```

После запуска приложение доступно по адресу: [http://localhost:8080](http://localhost:8080)

## Структура проекта

```
src/main/kotlin/ru/yzuykov/springmvcdemo/
├── controller/        # NewsController: `/`, `/news`
├── service/
│   ├── api/           # NewsService interface
│   └── impl/          # NewsServiceImpl
├── client/            # NewsHttpClient (@HttpExchange)
├── config/            # NewsProperties
└── model/             # DTO
```

## Конфигурация

Настройки NewsAPI указываются в `application.yaml`:

```yaml
news:
  apiKey: ${NEWS_API_KEY:demo}
```

Ключ передаётся через переменную окружения `NEWS_API_KEY`.

## Тесты

```bash
./gradlew test
```

Отчёт покрытия JaCoCo: `build/reports/jacoco/test/html/index.html`
