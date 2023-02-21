package com.gildedrose.web

import com.gildedrose.web.items.ITEMS_PATH
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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WebApplicationIT {
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    fun `should returns items`() {
        mockMvc
            .perform(get(ITEMS_PATH))
            .andExpect(status().isOk)
            .andExpect(content().json("[]"))


        mockMvc
            .perform(post(ITEMS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {"name":"Orange","quality":10,"registeredOn":"2023-01-01","sellBy":"2023-01-05"}
            """.trimIndent()))

        mockMvc
            .perform(get(ITEMS_PATH))
            .andExpect(status().isOk)
            .andExpect(content().json("""
                [
                  {
                    "name": "Orange",
                    "quality": 10,
                    "sellIn": 4
                  }
                ]
                """.trimIndent()))
    }
}