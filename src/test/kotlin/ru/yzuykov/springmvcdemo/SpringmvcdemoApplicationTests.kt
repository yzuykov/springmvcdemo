package ru.yzuykov.springmvcdemo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import ru.yzuykov.springmvcdemo.config.NewsProperties
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue

@SpringBootTest
@TestPropertySource(properties = ["news.api-key=test-key", "news.sources=test-sources", "news.url=https://test.com"])
class SpringmvcdemoApplicationTests {

	@Autowired
	lateinit var newsProperties: NewsProperties

	@Test
	fun contextLoads() {
	}

	@Test
	fun `newsProperties bean is loaded`() {
		assertNotNull(newsProperties)
	}

	@Test
	fun `newsProperties has correct values`() {
		assertTrue(newsProperties.apiKey == "test-key")
		assertTrue(newsProperties.sources == "test-sources")
		assertTrue(newsProperties.url == "https://test.com")
	}
}
