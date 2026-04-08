package ru.yzuykov.springmvcdemo.service.impl

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.yzuykov.springmvcdemo.client.NewsClient
import ru.yzuykov.springmvcdemo.config.NewsProperties
import ru.yzuykov.springmvcdemo.model.ArticleDto
import ru.yzuykov.springmvcdemo.model.NewsResponse
import ru.yzuykov.springmvcdemo.model.SourceDto
import java.time.Instant

class NewsServiceImplTest {

    private val newsClient: NewsClient = mockk()
    private val newsProperties = NewsProperties(
        apiKey = "test-api-key",
        sources = "test-sources",
        url = "https://test.com"
    )
    private val newsService = NewsServiceImpl(newsClient, newsProperties)

    @Test
    fun `getArticlesList returns articles when client returns data`() {
        // given
        val articles = listOf(
            ArticleDto(
                source = SourceDto("source-1", "Test Source"),
                author = "Test Author",
                title = "Test Article",
                description = "Test Description",
                url = "https://test.com/article",
                urlToImage = "https://test.com/image.jpg",
                publishedAt = Instant.now()
            )
        )
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 1,
            articles = articles
        )

        every { newsClient.getTopHeadLines(newsProperties.apiKey, newsProperties.sources) } returns newsResponse

        // when
        val result = newsService.getArticlesList()

        // then
        assertEquals(1, result.size)
        assertEquals("Test Article", result[0].title)
        assertEquals("Test Author", result[0].author)
    }

    @Test
    fun `getArticlesList returns empty list when client throws exception`() {
        // given
        every { newsClient.getTopHeadLines(newsProperties.apiKey, newsProperties.sources) } throws RuntimeException("API error")

        // when
        val result = newsService.getArticlesList()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getArticlesList returns empty list when articles list is empty`() {
        // given
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 0,
            articles = emptyList()
        )

        every { newsClient.getTopHeadLines(newsProperties.apiKey, newsProperties.sources) } returns newsResponse

        // when
        val result = newsService.getArticlesList()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getArticlesList passes correct API key and sources to client`() {
        // given
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 0,
            articles = emptyList()
        )

        every { newsClient.getTopHeadLines(newsProperties.apiKey, newsProperties.sources) } returns newsResponse

        // when
        newsService.getArticlesList()

        // then - verify the correct parameters were passed
        io.mockk.verify { newsClient.getTopHeadLines(newsProperties.apiKey, newsProperties.sources) }
    }
}
