package ru.yzuykov.springmvcdemo.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.yzuykov.springmvcdemo.client.NewsClient
import ru.yzuykov.springmvcdemo.config.NewsProperties
import ru.yzuykov.springmvcdemo.model.ArticleDto
import ru.yzuykov.springmvcdemo.service.api.NewsService

@Service
class NewsServiceImpl @Autowired constructor(val newsClient: NewsClient, val newsProperties: NewsProperties) : NewsService {

    override fun getArticlesList(): List<ArticleDto> {
        return newsClient.getTopHeadLines(newsProperties.apiKey, newsProperties.sources)?.articles ?: listOf()
    }
}