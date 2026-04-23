package ru.yzuykov.springmvcdemo.service.impl

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.yzuykov.springmvcdemo.client.NewsHttpClient
import ru.yzuykov.springmvcdemo.config.NewsProperties
import ru.yzuykov.springmvcdemo.model.ArticleDto
import ru.yzuykov.springmvcdemo.service.api.NewsService

@Service
class NewsServiceImpl(
    private val newsHttpClient: NewsHttpClient,
    private val newsProperties: NewsProperties
) : NewsService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun getArticlesList(): List<ArticleDto> {
        return try {
            val sourcesResponse = newsHttpClient.getSources("ru", newsProperties.apiKey)
            val sources = sourcesResponse.sources.joinToString(",") { it.id }
            log.info("Fetched {} sources: {}", sourcesResponse.sources.size, sources)
            val response = newsHttpClient.getTopHeadLines(sources, newsProperties.apiKey)
            log.info("Got {} articles", response.articles.size)
            response.articles
        } catch (e: Exception) {
            log.error("Failed to fetch news from NewsAPI", e)
            emptyList()
        }
    }
}