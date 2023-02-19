package com.gildedrose.web.items

import com.gildedrose.domain.N
import com.gildedrose.domain.Stock
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.Item
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.quality.Standard
import com.gildedrose.usecases.GetStock
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest
class ItemsControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var getStock: GetStock

    @Test
    fun `GET - should list all items`() {
        val items: List<Item> = listOf(
            ValidItem(N("Orange")!!, JustValid(Valid(ShelfLife.NOW)!!), Standard.of(9)!!),
        )

        whenever(getStock.asOf(any())).thenReturn(Stock.of(items))

        mockMvc.perform(get("/items").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$[0].name", `is`("Orange")))
            .andExpect(jsonPath("\$[0].quality", `is`(9)))
    }
}