package ru.yzuykov.springmvcdemo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import ru.yzuykov.springmvcdemo.service.api.NewsService

@Controller
class NewsController @Autowired constructor(val newsService: NewsService) {

    @GetMapping("/")
    fun welcome(model: Model): String {
        model.addAttribute("Name", "Yuriy")
        return "welcome"
    }

    @GetMapping("/news")
    fun news(model: Model): String {
        model.addAttribute("NewsList", newsService.getArticlesList())
        return "news"
    }
}