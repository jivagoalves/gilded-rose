package com.gildedrose.web.items

import com.gildedrose.domain.N
import com.gildedrose.domain.Stock
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.Item
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.quality.Standard
import com.gildedrose.usecases.IAddItemToStock
import com.gildedrose.usecases.IGetStock
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebMvcTest
class ItemsControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var getStock: IGetStock

    @Test
    fun `GET - should list all items`() {
        val jan1st = LocalDate.parse("2023-01-01")
        val jan5th = LocalDate.parse("2023-01-05")
        val items: List<Item> = listOf(
            ValidItem(N("Orange")!!, JustValid(Valid(ShelfLife(jan1st, jan5th))!!), Standard.of(9)!!),
        )

        whenever(getStock.asOf(any())).thenReturn(Stock.of(items))

        mockMvc
            .perform(get(ITEMS_PATH).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$[0].name", `is`("Orange")))
            .andExpect(jsonPath("\$[0].quality", `is`(9)))
            .andExpect(jsonPath("\$[0].sellIn", `is`(5)))
    }

    @MockBean
    private lateinit var addItemToStock: IAddItemToStock

    @Test
    fun `POST - should create a new item`() {
        val itemDTO = ItemRequestDTO(
            "Orange",
            10,
            "2023-01-01",
            "2023-01-05",
        )

        mockMvc.perform(
            post(ITEMS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(itemDTO)))
            .andExpect(status().isCreated)

        verify(addItemToStock).addItem(itemDTO.toValidItem())
    }

    private fun json(itemDTO: ItemRequestDTO): String =
        ObjectMapper().writeValueAsString(itemDTO)
}