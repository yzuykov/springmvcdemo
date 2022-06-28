package ru.yzuykov.springmvcdemo.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.yzuykov.springmvcdemo.entity.Article
import ru.yzuykov.springmvcdemo.entity.NewsResponse
import ru.yzuykov.springmvcdemo.service.api.NewsService

@Service
class NewsServiceImpl @Autowired constructor(val restTemplate: RestTemplate) : NewsService {

    @Value("\${news.url}")
    lateinit var url: String

    override fun getNewsList(): List<Article> {
        return restTemplate.getForEntity(url, NewsResponse::class.java).body?.articles ?: emptyList()
    }
}