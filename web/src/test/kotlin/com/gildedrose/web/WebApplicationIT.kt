package com.gildedrose.web

import com.gildedrose.web.api.ITEMS_PATH
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WebApplicationIT {
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    private val today = LocalDate.now()
    private val yesterday = today.minusDays(1)
    private val tomorrow = today.plusDays(1)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @Test
    fun `should return all items`() {
        mockMvc
            .perform(get(ITEMS_PATH))
            .andExpect(status().isOk)
            .andExpect(content().json("[]"))


        val body =
            """
            {
                "name":"Orange",
                "quality":10,
                "registeredOn":"${formatter.format(yesterday)}",
                "sellBy":"${formatter.format(tomorrow)}"
            }
            """.trimIndent()
        mockMvc.perform(
            post(ITEMS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )

        val expectedBody =
            """
            [
                {
                    "name": "Orange",
                    "quality": 9,
                    "sellIn": 2
                }
            ]
            """.trimIndent()
        mockMvc
            .perform(get(ITEMS_PATH))
            .andExpect(status().isOk)
            .andExpect(content().json(expectedBody))
    }
}