 package ru.yzuykov.springmvcdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import ru.yzuykov.springmvcdemo.config.NewsProperties

@EnableFeignClients
@EnableConfigurationProperties(NewsProperties::class)
@SpringBootApplication
class SpringmvcdemoApplication

fun main(args: Array<String>) {
    runApplication<SpringmvcdemoApplication>(*args)
}
