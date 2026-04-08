package ru.yzuykov.springmvcdemo.model

import java.time.Instant

data class NewsResponse (val status: String,
                         val totalResults: Int,
                         val articles: List<ArticleDto>)

data class ArticleDto (val source: SourceDto,
                       val author: String?,
                       val title: String,
                       val description: String?,
                       val url: String,
                       val urlToImage: String?,
                       val publishedAt: Instant?)

data class SourceDto(val id: String,
                     val name: String)
