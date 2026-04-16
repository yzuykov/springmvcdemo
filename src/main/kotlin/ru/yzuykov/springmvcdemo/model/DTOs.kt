package ru.yzuykov.springmvcdemo.model

import java.time.Instant

data class NewsResponse (val status: String,
                         val totalResults: Int,
                         val articles: List<ArticleDto>)

data class SourcesResponse (val status: String,
                           val sources: List<SourceDto>)                         

data class ArticleDto (val source: SourceDto,
                       val author: String?,
                       val title: String,
                       val description: String?,
                       val content: String?,
                       val url: String,
                       val urlToImage: String?,
                       val publishedAt: Instant?)

data class SourceDto(val id: String,
                     val name: String)
