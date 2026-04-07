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
import java.util.Date

class NewsServiceImplTest {

    private val newsClient: NewsClient = mockk()
    private val newsProperties: NewsProperties = mockk()
    private val newsService = NewsServiceImpl(newsClient, newsProperties)

    @Test
    fun `getArticlesList returns articles when client returns data`() {
        // given
        val apiKey = "test-api-key"
        val sources = "test-sources"
        val articles = listOf(
            ArticleDto(
                source = SourceDto("source-1", "Test Source"),
                author = "Test Author",
                title = "Test Article",
                description = "Test Description",
                url = "https://test.com/article",
                urlToImage = "https://test.com/image.jpg",
                publishedAt = Date()
            )
        )
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 1,
            articles = articles
        )

        every { newsProperties.apiKey } returns apiKey
        every { newsProperties.sources } returns sources
        every { newsClient.getTopHeadLines(apiKey, sources) } returns newsResponse

        // when
        val result = newsService.getArticlesList()

        // then
        assertEquals(1, result.size)
        assertEquals("Test Article", result[0].title)
        assertEquals("Test Author", result[0].author)
    }

    @Test
    fun `getArticlesList returns empty list when client returns null`() {
        // given
        val apiKey = "test-api-key"
        val sources = "test-sources"

        every { newsProperties.apiKey } returns apiKey
        every { newsProperties.sources } returns sources
        every { newsClient.getTopHeadLines(apiKey, sources) } returns null

        // when
        val result = newsService.getArticlesList()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getArticlesList returns empty list when articles list is empty`() {
        // given
        val apiKey = "test-api-key"
        val sources = "test-sources"
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 0,
            articles = emptyList()
        )

        every { newsProperties.apiKey } returns apiKey
        every { newsProperties.sources } returns sources
        every { newsClient.getTopHeadLines(apiKey, sources) } returns newsResponse

        // when
        val result = newsService.getArticlesList()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getArticlesList passes correct API key and sources to client`() {
        // given
        val apiKey = "my-api-key"
        val sources = "bbc-news,cnn"

        every { newsProperties.apiKey } returns apiKey
        every { newsProperties.sources } returns sources
        every { newsClient.getTopHeadLines(apiKey, sources) } returns null

        // when
        newsService.getArticlesList()

        // then - verify the correct parameters were passed
        io.mockk.verify { newsClient.getTopHeadLines(apiKey, sources) }
    }
}
