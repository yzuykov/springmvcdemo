package ru.yzuykov.springmvcdemo.service.api

import ru.yzuykov.springmvcdemo.entity.Article

interface NewsService {
    fun getNewsList(): List<Article>
}