package ru.yzuykov.springmvcdemo.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import ru.yzuykov.springmvcdemo.model.NewsResponse
import ru.yzuykov.springmvcdemo.model.SourcesResponse

@FeignClient(name = "newsClient", url = "\${news.url}")
interface NewsClient {

    @GetMapping("/v2/top-headlines")
    fun getTopHeadLines(
        @RequestHeader("X-Api-Key") apiKey: String,
        @RequestParam("sources") sources: String
    ): NewsResponse

    @GetMapping("/v2/top-headlines/sources")
    fun getSources(
        @RequestHeader("X-Api-Key") apiKey: String,
        @RequestParam("language") language: String
    ): SourcesResponse
}