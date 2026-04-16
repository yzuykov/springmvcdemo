package ru.yzuykov.springmvcdemo.service.impl

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.yzuykov.springmvcdemo.client.NewsClient
import ru.yzuykov.springmvcdemo.config.NewsProperties
import ru.yzuykov.springmvcdemo.model.ArticleDto
import ru.yzuykov.springmvcdemo.service.api.NewsService

@Service
class NewsServiceImpl(
    private val newsClient: NewsClient,
    private val newsProperties: NewsProperties
) : NewsService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun getArticlesList(): List<ArticleDto> {
        return try {
            val sourcesResponse = newsClient.getSources(newsProperties.apiKey, "ru")
            val sources = sourcesResponse.sources.joinToString(",") { it.id }
            log.info("Fetched {} sources: {}", sourcesResponse.sources.size, sources)
            val response = newsClient.getTopHeadLines(newsProperties.apiKey, sources)
            log.info("Got {} articles", response.articles.size)
            response.articles
        } catch (e: Exception) {
            log.error("Failed to fetch news from NewsAPI", e)
            emptyList()
        }
    }
}