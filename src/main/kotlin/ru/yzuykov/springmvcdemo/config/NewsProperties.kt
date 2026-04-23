package ru.yzuykov.springmvcdemo.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "news")
data class NewsProperties(
    val apiKey: String = "",
    val url: String = "",
    val sources: String = ""
)