package ru.yzuykov.springmvcdemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {

    @Bean
    fun newsProperties(): NewsProperties {
        return NewsProperties()
    }
}