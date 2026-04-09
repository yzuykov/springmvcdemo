package ru.yzuykov.springmvcdemo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(properties = ["news.api-key=test-key", "news.sources=test-sources", "news.url=https://test.com"])
class SpringmvcdemoApplicationTests {

	@Test
	fun contextLoads() {
	}

}
