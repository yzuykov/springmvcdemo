package ru.yzuykov.springmvcdemo.service.api

import ru.yzuykov.springmvcdemo.model.ArticleDto

interface NewsService {
    fun getNewsList(): List<ArticleDto>
}