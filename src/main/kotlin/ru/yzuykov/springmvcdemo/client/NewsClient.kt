package ru.yzuykov.springmvcdemo.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import ru.yzuykov.springmvcdemo.model.NewsResponse

@FeignClient(name = "newsClient", url = "\${news.url}")
interface NewsClient {

    @GetMapping("/top-headlines")
    fun getTopHeadLines(
        @RequestHeader("X-Api-Key") apiKey: String,
        @RequestParam("sources") sources: String
    ): NewsResponse
}