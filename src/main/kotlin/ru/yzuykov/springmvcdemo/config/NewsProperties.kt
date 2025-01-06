package ru.yzuykov.springmvcdemo.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "news")
data class NewsProperties(var apiKey: String = "",
                          var sources: String = "",
                          var url: String = "")