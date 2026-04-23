package ru.yzuykov.springmvcdemo.client

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import ru.yzuykov.springmvcdemo.model.NewsResponse
import ru.yzuykov.springmvcdemo.model.SourcesResponse

@HttpExchange("https://newsapi.org")
interface NewsHttpClient {

    @GetExchange("/v2/top-headlines")
    fun getTopHeadLines(
        @RequestParam sources: String,
        @RequestHeader("X-Api-Key") apiKey: String
    ): NewsResponse

    @GetExchange("/v2/top-headlines/sources")
    fun getSources(
        @RequestParam language: String,
        @RequestHeader("X-Api-Key") apiKey: String
    ): SourcesResponse
}