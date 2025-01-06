package ru.yzuykov.springmvcdemo.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.yzuykov.springmvcdemo.model.ArticleDto
import ru.yzuykov.springmvcdemo.model.NewsResponse
import ru.yzuykov.springmvcdemo.service.api.NewsService

@Service
class NewsServiceImpl @Autowired constructor(val restTemplate: RestTemplate) : NewsService {

    @Value("\${news.url}")
    lateinit var url: String

    override fun getNewsList(): List<ArticleDto> {
        return restTemplate.getForEntity(url, NewsResponse::class.java).body?.articles ?: listOf()
    }
}