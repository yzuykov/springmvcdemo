# Bootstrap → Native CSS Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Remove Bootstrap 5.3.3 (WebJars) and replace with ~120 lines of custom CSS using CSS variables and component classes.

**Architecture:** One CSS file with design tokens (`:root`), component classes (`.navbar`, `.news-card`), and minimal utilities. Three Thymeleaf templates updated to use new classes. Bootstrap JS replaced by checkbox hack for navbar collapse.

**Tech Stack:** CSS (no frameworks), HTML/Thymeleaf, Gradle (WebJars removal)

---

### Task 1: Create CSS file, update build config, connect CSS to templates

**Files:**
- Create: `src/main/resources/static/css/style.css`
- Modify: `build.gradle.kts:32-34`
- Modify: `src/main/resources/templates/fragments/header.html:4-8`

- [ ] **Step 1: Create `style.css` with design tokens**

```css
:root {
  --color-primary: #0d6efd;
  --color-secondary: #20c997;
  --color-dark: #212529;
  --color-muted: #6c757d;
  --color-secondary-text: #6c757d;
  --color-body-bg: #fff;
  --color-card-bg: #fff;
  --color-border: rgba(0, 0, 0, 0.125);
  --navbar-gradient: linear-gradient(90deg, #0d6efd, #20c997);
  --spacing-xs: 0.25rem;
  --spacing-sm: 0.5rem;
  --spacing-md: 1rem;
  --spacing-lg: 1.5rem;
  --spacing-xl: 3rem;
  --font-size-sm: 0.875rem;
  --font-size-base: 1rem;
  --radius: 0.375rem;
  --container-max-width: 1140px;
  --breakpoint-md: 768px;
}
```

- [ ] **Step 2: Remove Bootstrap WebJars from `build.gradle.kts`**

Remove these lines:
```kotlin
// Webjars
implementation("org.webjars:webjars-locator:0.52")
implementation("org.webjars.npm:bootstrap:5.3.3")
```

- [ ] **Step 3: Update `header.html` — replace Bootstrap links with custom CSS**

In `header.html`, in the `header-css` fragment, replace the Bootstrap CSS `<link>` and JS `<script>` with:
```html
<link rel="stylesheet" type="text/css" th:href="@{/css/style.css}" />
```

- [ ] **Step 4: Verify build compiles**

Run: `./gradlew build`

---

### Task 2: Rewrite navbar (checkbox hack, custom CSS)

**Files:**
- Modify: `src/main/resources/templates/fragments/header.html` (navbar)
- Modify: `src/main/resources/static/css/style.css` (append navbar CSS)

- [ ] **Step 1: Add navbar + reset CSS to `style.css`**

Append to `style.css`:
```css
*, *::before, *::after {
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: system-ui, -apple-system, "Segoe UI", Roboto, sans-serif;
  font-size: var(--font-size-base);
  color: var(--color-dark);
  background: var(--color-body-bg);
}

a {
  color: var(--color-primary);
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

/* Navbar */
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: var(--spacing-sm) var(--spacing-md);
  background: var(--navbar-gradient);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.navbar-brand {
  color: #fff;
  font-size: var(--spacing-lg);
  font-weight: 600;
  text-decoration: none;
}

.navbar-brand:hover {
  text-decoration: none;
  opacity: 0.9;
}

.nav-links {
  list-style: none;
  margin: 0;
  padding: 0;
  display: none;
  flex-direction: column;
  gap: var(--spacing-xs);
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background: var(--navbar-gradient);
  padding: var(--spacing-sm) var(--spacing-md);
}

.nav-toggle:checked ~ .nav-links {
  display: flex;
}

.nav-link {
  color: rgba(255, 255, 255, 0.85);
  text-decoration: none;
  padding: var(--spacing-xs) 0;
  display: block;
}

.nav-link:hover {
  color: #fff;
  text-decoration: none;
}

.nav-link.active {
  color: #fff;
  font-weight: 600;
}

/* Navbar toggler (hamburger) */
.nav-toggler {
  display: flex;
  flex-direction: column;
  gap: 5px;
  cursor: pointer;
  padding: 4px;
}

.nav-toggler span {
  display: block;
  width: 25px;
  height: 2px;
  background: #fff;
  border-radius: 2px;
  transition: transform 0.2s;
}

@media (min-width: 768px) {
  .nav-toggler {
    display: none;
  }

  .nav-links {
    display: flex;
    flex-direction: row;
    position: static;
    background: none;
    padding: 0;
    gap: var(--spacing-md);
  }

  .nav-link {
    padding: 0;
  }
}
```

- [ ] **Step 2: Rewrite navbar in `header.html`**

Replace the `navigate` fragment with:
```html
<th:block th:fragment="navigate">
  <nav class="navbar">
    <a class="navbar-brand" th:href="@{/}">Spring Boot</a>
    <input type="checkbox" id="nav-toggle" class="nav-toggle" hidden>
    <label for="nav-toggle" class="nav-toggler">
      <span></span><span></span><span></span>
    </label>
    <ul class="nav-links">
      <li><a class="nav-link active" th:href="@{/}">Home</a></li>
      <li><a class="nav-link" th:href="@{/news}">News</a></li>
    </ul>
  </nav>
</th:block>
```

- [ ] **Step 3: Verify build**

Run: `./gradlew build`

---

### Task 3: Add grid, cards, utilities to CSS; update news page

**Files:**
- Modify: `src/main/resources/static/css/style.css` (append remaining CSS)
- Modify: `src/main/resources/templates/news.html` (replace all Bootstrap classes)
- Modify: `src/main/resources/templates/welcome.html`

- [ ] **Step 1: Add container, grid, card, and utility CSS**

Append to `style.css`:
```css
/* Container */
.container,
.container-fluid {
  padding: 0 var(--spacing-md);
  margin-inline: auto;
}

.container {
  max-width: var(--container-max-width);
}

/* News grid */
.news-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: var(--spacing-lg);
}

/* News card */
.news-card {
  display: flex;
  flex-direction: column;
  border: 1px solid var(--color-border);
  border-radius: var(--radius);
  overflow: hidden;
  background: var(--color-card-bg);
}

.news-card__image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.news-card__body {
  padding: var(--spacing-md);
  display: flex;
  flex-direction: column;
  gap: var(--spacing-sm);
  flex: 1;
}

.news-card__meta {
  font-size: var(--font-size-sm);
  color: var(--color-muted);
}

.news-card__title {
  margin: 0;
  font-size: 1.125rem;
}

.news-card__title a {
  color: var(--color-dark);
  text-decoration: none;
}

.news-card__title a:hover {
  color: var(--color-primary);
  text-decoration: underline;
}

.news-card__text {
  flex: 1;
  color: var(--color-dark);
}

/* Utilities */
.mb-1 { margin-bottom: var(--spacing-xs); }
.mb-3 { margin-bottom: var(--spacing-md); }
.mb-4 { margin-bottom: var(--spacing-lg); }
.mb-0 { margin-bottom: 0; }
.py-5 { padding-block: var(--spacing-xl); }
.text-muted { color: var(--color-muted); }
.text-secondary { color: var(--color-secondary-text); }
.text-dark { color: var(--color-dark); }
.text-center { text-align: center; }
.small { font-size: var(--font-size-sm); }
.d-flex { display: flex; }
.flex-column { flex-direction: column; }
.flex-grow-1 { flex-grow: 1; }

/* Empty state */
.empty-state-icon {
  color: var(--color-muted);
}
```

- [ ] **Step 2: Rewrite `news.html`**

Replace entire file content with:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>News - Springmvcdemo</title>
    <div th:replace="~{fragments/header :: header-css}"></div>
</head>
<body>
<div th:replace="~{fragments/header :: navigate}"></div>
<div class="container">
    <h1 class="mb-4">News:</h1>

    <div th:if="${#lists.isEmpty(newsList)}" class="text-center py-5">
        <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="empty-state-icon mb-3" viewBox="0 0 16 16">
            <path d="M0 2.5A1.5 1.5 0 0 1 1.5 1h11A1.5 1.5 0 0 1 14 2.5v10.528c0 .3-.05.654-.238.972h.738a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 1 1 0v9a1.5 1.5 0 0 1-1.5 1.5H1.497A1.497 1.497 0 0 1 0 13.5v-11zM12 14c.37 0 .654-.05.972-.238A.5.5 0 0 0 13 13.5v-11A.5.5 0 0 0 12.5 2h-11A.5.5 0 0 0 1 2.5v11c0 .37.05.654.238.972A.5.5 0 0 0 1.5 14h11z"/>
            <path d="M2.5 3h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1 0-1zm0 2h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1 0-1zm0 2h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1 0-1zm4.5-1a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1H7.5a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1H7.5a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1H7.5a.5.5 0 0 1-.5-.5z"/>
        </svg>
        <h3 class="text-muted">No news available</h3>
        <p class="text-secondary">Try again later or check your API configuration</p>
    </div>

    <div class="news-grid" th:unless="${#lists.isEmpty(newsList)}">
        <div th:each="article : ${newsList}" class="news-card">
            <img th:if="${article.urlToImage != null}"
                 th:src="${article.urlToImage}" class="news-card__image" alt="News image">
            <div class="news-card__body">
                <p class="news-card__meta"
                   th:text="${article.publishedAt != null ? #temporals.format(article.publishedAt, 'dd-MM-yyyy HH:mm') : 'N/A'}"></p>
                <h5 class="news-card__title">
                    <a th:href="${article.url}" target="_blank" rel="noopener noreferrer"
                       th:text="${article.title}"></a>
                </h5>
                <p class="news-card__text" th:text="${article.description ?: 'No description'}"></p>
                <p class="news-card__meta"
                   th:text="'By ' + ${article.author ?: 'Unknown'}"></p>
            </div>
        </div>
    </div>
</div>

</body>
</html>
```

- [ ] **Step 3: Update `welcome.html`** — change `container-fluid` to `container-fluid` (already supported in our CSS)

- [ ] **Step 4: Verify build**

Run: `./gradlew build`

---

### Task 4: Test and verify

- [ ] **Step 1: Run tests**

Run: `./gradlew test`

- [ ] **Step 2: Run app and check pages load**

Run: `./gradlew bootRun`
Visit: `http://localhost:8080/` and `http://localhost:8080/news`

- [ ] **Step 3: Commit**

```bash
git add -A
git commit -m "feat: replace bootstrap with custom css"
```
