package com.gildedrose.web.api

import arrow.core.*
import com.gildedrose.domain.Stock
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.contracts.lifecycle.ValidShelfLife
import com.gildedrose.domain.items.*
import com.gildedrose.domain.items.ValidationError.*
import com.gildedrose.usecases.IAddItemToStock
import com.gildedrose.usecases.IGetStock
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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
import java.time.format.DateTimeFormatter

@ExtendWith(SpringExtension::class)
@WebMvcTest
@DisplayName(ITEMS_PATH)
class ItemsControllerTest {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var getStock: IGetStock

    @MockBean
    private lateinit var addItemToStock: IAddItemToStock

    private val jan1st = LocalDate.parse("2023-01-01")
    private val jan5th = LocalDate.parse("2023-01-05")
    private val now = LocalDate.now()

    @Test
    fun `GET - should list all items`() {
        val items: List<Item> = listOf(
            ValidItem(N("Orange")!!, JustValid(Valid(ShelfLife(jan1st, jan5th))!!), StandardQuality.of(9)!!),
        )

        whenever(getStock.asOf(now)).thenReturn(Stock.of(items))

        mockMvc
            .perform(get(ITEMS_PATH).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$[0].name", `is`("Orange")))
            .andExpect(jsonPath("\$[0].quality", `is`(9)))
            .andExpect(jsonPath("\$[0].sellIn", `is`(5)))
    }

    @Nested
    @DisplayName(AS_OF_PATH)
    inner class AsOfPathTest {
        private val tomorrow = now.plusDays(1)
        private val url = "$ITEMS_PATH/$AS_OF_PATH/${formatter.format(tomorrow)}"

        @Test
        fun `GET - should list all items as of date`() {
            val items: List<Item> = listOf(
                ValidItem(N("Orange")!!, JustValid(Valid(ShelfLife(jan1st, jan5th))!!), StandardQuality.of(9)!!),
            )

            whenever(getStock.asOf(tomorrow)).thenReturn(Stock.of(items))

            mockMvc
                .perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$[0].name", `is`("Orange")))
                .andExpect(jsonPath("\$[0].quality", `is`(9)))
                .andExpect(jsonPath("\$[0].sellIn", `is`(5)))
        }

    }

    @Test
    fun `POST - should create a new item`() {
        val itemDTO = ItemDTORequest(
            name = "Orange",
            quality = 10,
            registeredOn = "2023-01-01",
            sellBy = "2023-01-05",
        )
        val expectedItem = ValidItem(
            name = N("Orange")!!,
            lifecycle = JustValid(Valid(ShelfLife(jan1st, jan5th))!!),
            quality = StandardQuality.of(10)!!
        )

        whenever(addItemToStock.addItem(itemDTO)).thenReturn(expectedItem.valid())

        mockMvc.perform(
            post(ITEMS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(itemDTO))
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("location", ITEMS_PATH))
    }

    @Test
    fun `POST - should return validation errors`() {
        val itemDTO = ItemDTORequest(
            name = "",
            quality = -1,
            registeredOn = "2023-01-05",
            sellBy = "2023-01-01",
        )

        whenever(addItemToStock.addItem(itemDTO)).thenReturn(
            listOf(
                Name.BlankName,
                StandardQuality.InvalidQuality,
                ValidShelfLife.InvalidLifecycle,
            ).invalid()
        )

        mockMvc.perform(
            post(ITEMS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(itemDTO))
        )
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$[0]", `is`(Name.BlankName.description)))
            .andExpect(jsonPath("\$[1]", `is`(StandardQuality.InvalidQuality.description)))
            .andExpect(jsonPath("\$[2]", `is`(ValidShelfLife.InvalidLifecycle.description)))
    }

    private fun json(itemDTO: ItemDTORequest): String =
        ObjectMapper().writeValueAsString(itemDTO)
}
