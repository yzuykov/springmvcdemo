package ru.yzuykov.springmvcdemo.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.yzuykov.springmvcdemo.entity.Article
import ru.yzuykov.springmvcdemo.entity.NewsResponse

@Service
class NewsService
{
    val restTemplate : RestTemplate = RestTemplate()
    val url : String = "https://newsapi.org/v2/top-headlines?sources=google-news-ru&apiKey=d0f1fd5097b641d88df662c46241248b"

    fun getNewsList(): List<Article>
    {
        return (restTemplate.getForEntity(url, Class.forName("ru.yzuykov.springmvcdemo.entity.NewsResponse")).body as NewsResponse).articles
    }
}