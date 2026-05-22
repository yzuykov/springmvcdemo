# Bootstrap → Native CSS Migration — Design Spec

## Scope
Удалить Bootstrap 5.3.3 (WebJars), заменить кастомным CSS на CSS-переменных. 3 Thymeleaf-шаблона, ~10 CSS-переменных, ~100-150 строк CSS.

## Design Tokens (`:root`)
- **Colors:** `--color-primary: #0d6efd`, `--color-secondary: #20c997`, `--color-dark: #212529`, `--color-muted: #6c757d`, `--color-body-bg`, `--color-card-bg`
- **Gradient:** `--navbar-gradient: linear-gradient(90deg, #0d6efd, #20c997)`
- **Spacing:** `--spacing-xs` (0.25rem), `--spacing-sm` (0.5), `--spacing-md` (1), `--spacing-lg` (1.5), `--spacing-xl` (3)
- **Typography:** `--font-size-sm: 0.875rem`, `--font-size-base: 1rem`
- **Layout:** `--radius: 0.375rem`, `--container-max-width: 1140px`, `--breakpoint-md: 768px`

## Components

### Navbar
- `position: sticky; top: 0` + gradient background
- Flexbox: brand left, toggler right (mobile), links right (desktop)
- **Mobile collapse:** Checkbox hack (`#nav-toggle:checked ~ .nav-links`)
- Toggler: 3 `<span>` полоски, `display: none` на `md+`
- `.nav-links`: `display: none` на mobile, `display: flex` на `md+`
- Активный пункт: `.nav-link.active` с `font-weight: 600`

### Grid
- `container`: `max-width: var(--container-max-width); margin-inline: auto; padding: 0 var(--spacing-md)`
- `container-fluid`: `padding: 0 var(--spacing-md)` (без max-width)
- `news-grid`: `display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: var(--spacing-lg)`

### Cards (BEM)
- `.news-card`: border, radius, overflow hidden, flex column, bg white
- `.news-card__image`: `height: 200px; object-fit: cover; width: 100%`
- `.news-card__body`: padding, flex column, gap between elements
- `.news-card__title`: link `text-decoration: none; color: var(--color-dark)`
- `.news-card__meta`: `font-size: var(--font-size-sm); color: var(--color-muted)`

### Utilities (только используемые)
- **Spacing:** `.mb-1/3/4/0`, `.py-5`
- **Text:** `.text-muted`, `.text-secondary`, `.text-dark`, `.text-center`, `.small`
- **Flex:** `.d-flex`, `.flex-column`, `.flex-grow-1`

## Empty State
- Inline SVG: убрать бесполезный класс `.bi-newspaper`, заменить на `.empty-state-icon`

## Files

| Action | File |
|---|---|
| **Create** | `src/main/resources/static/css/style.css` |
| **Edit** | `src/main/resources/templates/fragments/header.html` |
| **Edit** | `src/main/resources/templates/news.html` |
| **Edit** | `src/main/resources/templates/welcome.html` |
| **Edit** | `build.gradle.kts` — удалить bootstrap + webjars-locator |
