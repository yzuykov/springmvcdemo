package ru.yzuykov.springmvcdemo.service.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.yzuykov.springmvcdemo.client.NewsClient
import ru.yzuykov.springmvcdemo.config.NewsProperties
import ru.yzuykov.springmvcdemo.model.ArticleDto
import ru.yzuykov.springmvcdemo.model.NewsResponse
import ru.yzuykov.springmvcdemo.model.SourceDto
import ru.yzuykov.springmvcdemo.model.SourcesResponse
import java.time.Instant

class NewsServiceImplTest {

    private val newsClient: NewsClient = mockk()
    private val newsProperties = NewsProperties(
        apiKey = "test-api-key",
        sources = "test-sources",
        url = "https://test.com"
    )
    private val newsService = NewsServiceImpl(newsClient, newsProperties)

    private val sourcesResponse = SourcesResponse(
        status = "ok",
        sources = listOf(
            SourceDto("rbc", "RBC"),
            SourceDto("ria", "RIA")
        )
    )

    @Test
    fun `getArticlesList returns articles when client returns data`() {
        // given
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
        val expectedSources = "rbc,ria"

        every { newsClient.getSources(newsProperties.apiKey, "ru") } returns sourcesResponse
        every { newsClient.getTopHeadLines(newsProperties.apiKey, expectedSources) } returns newsResponse

        // when
        val result = newsService.getArticlesList()

        // then
        assertEquals(1, result.size)
        assertEquals("Test Article", result[0].title)
    }

    @Test
    fun `getArticlesList returns empty list when getSources throws exception`() {
        // given
        every { newsClient.getSources(newsProperties.apiKey, "ru") } throws RuntimeException("API error")

        // when
        val result = newsService.getArticlesList()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getArticlesList returns empty list when getTopHeadLines throws exception`() {
        // given
        val expectedSources = "rbc,ria"
        every { newsClient.getSources(newsProperties.apiKey, "ru") } returns sourcesResponse
        every { newsClient.getTopHeadLines(newsProperties.apiKey, expectedSources) } throws RuntimeException("API error")

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
        val expectedSources = "rbc,ria"

        every { newsClient.getSources(newsProperties.apiKey, "ru") } returns sourcesResponse
        every { newsClient.getTopHeadLines(newsProperties.apiKey, expectedSources) } returns newsResponse

        // when
        val result = newsService.getArticlesList()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getArticlesList passes correct parameters to client`() {
        // given
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 0,
            articles = emptyList()
        )
        val expectedSources = "rbc,ria"

        every { newsClient.getSources(newsProperties.apiKey, "ru") } returns sourcesResponse
        every { newsClient.getTopHeadLines(any(), any()) } returns newsResponse

        // when
        newsService.getArticlesList()

        // then
        verify { newsClient.getSources(newsProperties.apiKey, "ru") }
        verify { newsClient.getTopHeadLines(newsProperties.apiKey, expectedSources) }
    }

    @Test
    fun `getArticlesList returns empty list when sources list is empty`() {
        // given
        val emptySourcesResponse = SourcesResponse(
            status = "ok",
            sources = emptyList()
        )
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 0,
            articles = emptyList()
        )

        every { newsClient.getSources(newsProperties.apiKey, "ru") } returns emptySourcesResponse
        every { newsClient.getTopHeadLines(any(), any()) } returns newsResponse

        // when
        val result = newsService.getArticlesList()

        // then
        assertTrue(result.isEmpty())
        verify { newsClient.getTopHeadLines(newsProperties.apiKey, "") }
    }

    @Test
    fun `getArticlesList handles articles with null content`() {
        // given
        val articles = listOf(
            ArticleDto(
                source = SourceDto("rbc", "RBC"),
                author = null,
                title = "Article without content",
                description = null,
                content = null,
                url = "https://test.com/article",
                urlToImage = null,
                publishedAt = null
            )
        )
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 1,
            articles = articles
        )
        val expectedSources = "rbc,ria"

        every { newsClient.getSources(newsProperties.apiKey, "ru") } returns sourcesResponse
        every { newsClient.getTopHeadLines(newsProperties.apiKey, expectedSources) } returns newsResponse

        // when
        val result = newsService.getArticlesList()

        // then
        assertEquals(1, result.size)
        assertEquals("Article without content", result[0].title)
        assertEquals(null, result[0].content)
    }

    @Test
    fun `getArticlesList returns multiple articles`() {
        // given
        val articles = listOf(
            ArticleDto(
                source = SourceDto("rbc", "RBC"),
                author = "Author 1",
                title = "Article 1",
                description = "Description 1",
                content = "Content 1",
                url = "https://test.com/1",
                urlToImage = "https://test.com/img1.jpg",
                publishedAt = Instant.now()
            ),
            ArticleDto(
                source = SourceDto("ria", "RIA"),
                author = "Author 2",
                title = "Article 2",
                description = "Description 2",
                content = "Content 2",
                url = "https://test.com/2",
                urlToImage = "https://test.com/img2.jpg",
                publishedAt = Instant.now()
            ),
            ArticleDto(
                source = SourceDto("rbc", "RBC"),
                author = "Author 3",
                title = "Article 3",
                description = "Description 3",
                content = "Content 3",
                url = "https://test.com/3",
                urlToImage = null,
                publishedAt = Instant.now()
            )
        )
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 3,
            articles = articles
        )
        val expectedSources = "rbc,ria"

        every { newsClient.getSources(newsProperties.apiKey, "ru") } returns sourcesResponse
        every { newsClient.getTopHeadLines(newsProperties.apiKey, expectedSources) } returns newsResponse

        // when
        val result = newsService.getArticlesList()

        // then
        assertEquals(3, result.size)
        assertEquals("Article 1", result[0].title)
        assertEquals("Article 2", result[1].title)
        assertEquals("Article 3", result[2].title)
    }
}
