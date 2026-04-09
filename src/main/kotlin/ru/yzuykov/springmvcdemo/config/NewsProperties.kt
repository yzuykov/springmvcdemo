package ru.yzuykov.springmvcdemo.config

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "news")
data class NewsProperties(
    @field:NotBlank val apiKey: String = "",
    @field:NotBlank val sources: String = "",
    @field:NotBlank val url: String = ""
)