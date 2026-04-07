package ru.yzuykov.springmvcdemo.controller

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.ui.Model
import ru.yzuykov.springmvcdemo.model.ArticleDto
import ru.yzuykov.springmvcdemo.model.SourceDto
import ru.yzuykov.springmvcdemo.service.api.NewsService
import java.util.Date

class NewsControllerTest {

    private val newsService: NewsService = mockk()
    private lateinit var controller: NewsController

    @BeforeEach
    fun setUp() {
        controller = NewsController(newsService)
    }

    @Test
    fun `welcome returns welcome view with name attribute`() {
        // given
        val model: Model = mockk(relaxed = true)

        // when
        val result = controller.welcome(model)

        // then
        assertEquals("welcome", result)
        io.mockk.verify { model.addAttribute("Name", "Yuriy") }
    }

    @Test
    fun `news returns news view with articles list`() {
        // given
        val articles = listOf(
            ArticleDto(
                source = SourceDto("source-1", "Test Source"),
                author = "Author 1",
                title = "Article 1",
                description = "Description 1",
                url = "https://test.com/article1",
                urlToImage = "https://test.com/image1.jpg",
                publishedAt = Date()
            ),
            ArticleDto(
                source = SourceDto("source-2", "Test Source 2"),
                author = "Author 2",
                title = "Article 2",
                description = "Description 2",
                url = "https://test.com/article2",
                urlToImage = "https://test.com/image2.jpg",
                publishedAt = Date()
            )
        )
        val model: Model = mockk(relaxed = true)

        every { newsService.getArticlesList() } returns articles

        // when
        val result = controller.news(model)

        // then
        assertEquals("news", result)
        io.mockk.verify { model.addAttribute("NewsList", articles) }
    }

    @Test
    fun `news passes empty list when service returns no articles`() {
        // given
        val model: Model = mockk(relaxed = true)
        every { newsService.getArticlesList() } returns emptyList()

        // when
        val result = controller.news(model)

        // then
        assertEquals("news", result)
        io.mockk.verify { model.addAttribute("NewsList", emptyList<ArticleDto>()) }
    }
}
