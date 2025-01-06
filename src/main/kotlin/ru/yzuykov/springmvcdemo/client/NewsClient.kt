package ru.yzuykov.springmvcdemo.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import ru.yzuykov.springmvcdemo.model.NewsResponse

@FeignClient(name = "newsClient", url = "\${news.url}")
interface NewsClient {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/top-headlines"],
        consumes = ["application/json"]
    )
    fun getTopHeadLines(
        @RequestParam(value = "apiKey") apiKey: String,
        @RequestParam(value = "sources") sources: String
    ): NewsResponse?
}