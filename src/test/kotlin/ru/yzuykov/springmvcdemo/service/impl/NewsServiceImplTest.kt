package ru.yzuykov.springmvcdemo.service.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.yzuykov.springmvcdemo.client.NewsHttpClient
import ru.yzuykov.springmvcdemo.config.NewsProperties
import ru.yzuykov.springmvcdemo.model.ArticleDto
import ru.yzuykov.springmvcdemo.model.NewsResponse
import ru.yzuykov.springmvcdemo.model.SourceDto
import ru.yzuykov.springmvcdemo.model.SourcesResponse
import java.time.Instant

class NewsServiceImplTest {

    private val newsHttpClient: NewsHttpClient = mockk()
    private val newsProperties = NewsProperties(
        apiKey = "test-api-key",
        sources = "test-sources",
        url = "https://test.com"
    )
    private val newsService = NewsServiceImpl(newsHttpClient, newsProperties)

    private val sourcesResponse = SourcesResponse(
        status = "ok",
        sources = listOf(
            SourceDto("rbc", "RBC"),
            SourceDto("ria", "RIA")
        )
    )

    @Test
    fun `getArticlesList returns articles when client returns data`() {
        val articles = listOf(
            ArticleDto(
                source = SourceDto("rbc", "RBC"),
                author = "Test Author",
                title = "Test Article",
                description = "Test Description",
                content = "Full test article content",
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

        every { newsHttpClient.getSources(any(), any()) } returns sourcesResponse
        every { newsHttpClient.getTopHeadLines(any(), any()) } returns newsResponse

        val result = newsService.getArticlesList()

        assertEquals(1, result.size)
        assertEquals("Test Article", result[0].title)
        verify { newsHttpClient.getSources("ru", newsProperties.apiKey) }
        verify { newsHttpClient.getTopHeadLines("rbc,ria", newsProperties.apiKey) }
    }

    @Test
    fun `getArticlesList returns empty list when sources call fails`() {
        every { newsHttpClient.getSources(any(), any()) } throws RuntimeException("API Error")

        val result = newsService.getArticlesList()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getArticlesList returns empty list when articles call fails`() {
        every { newsHttpClient.getSources(any(), any()) } returns sourcesResponse
        every { newsHttpClient.getTopHeadLines(any(), any()) } throws RuntimeException("API Error")

        val result = newsService.getArticlesList()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getArticlesList handles empty sources`() {
        val emptySourcesResponse = SourcesResponse(status = "ok", sources = emptyList())
        every { newsHttpClient.getSources(any(), any()) } returns emptySourcesResponse
        every { newsHttpClient.getTopHeadLines(any(), any()) } returns NewsResponse("ok", 0, emptyList())

        val result = newsService.getArticlesList()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getArticlesList logs fetched sources count`() {
        every { newsHttpClient.getSources(any(), any()) } returns sourcesResponse
        every { newsHttpClient.getTopHeadLines(any(), any()) } returns NewsResponse("ok", 0, emptyList())

        newsService.getArticlesList()

        verify { newsHttpClient.getSources("ru", newsProperties.apiKey) }
    }
}