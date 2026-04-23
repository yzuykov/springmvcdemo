package ru.yzuykov.springmvcdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.web.service.registry.ImportHttpServices
import ru.yzuykov.springmvcdemo.client.NewsHttpClient
import ru.yzuykov.springmvcdemo.config.NewsProperties

@EnableConfigurationProperties(NewsProperties::class)
@ImportHttpServices(types = [NewsHttpClient::class])
@SpringBootApplication
class SpringmvcdemoApplication

fun main(args: Array<String>) {
    runApplication<SpringmvcdemoApplication>(*args)
}