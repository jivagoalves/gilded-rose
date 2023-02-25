package com.gildedrose.web.items

import arrow.core.*
import com.gildedrose.domain.N
import com.gildedrose.domain.Name
import com.gildedrose.domain.Stock
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.contracts.lifecycle.ValidShelfLife
import com.gildedrose.domain.items.Item
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.items.ValidationError.*
import com.gildedrose.domain.quality.Standard
import com.gildedrose.usecases.IAddItemToStock
import com.gildedrose.usecases.IGetStock
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

    @MockBean
    private lateinit var addItemToStock: IAddItemToStock

    private val jan1st = LocalDate.parse("2023-01-01")
    private val jan5th = LocalDate.parse("2023-01-05")

    @Test
    fun `GET - should list all items`() {
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
            quality = Standard.of(10)!!
        )

        whenever(addItemToStock.addItem(itemDTO)).thenReturn(expectedItem.valid())

        mockMvc.perform(
            post(ITEMS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(itemDTO)))
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
                Standard.InvalidQuality,
                ValidShelfLife.InvalidLifecycle,
            ).invalid()
        )

        mockMvc.perform(
            post(ITEMS_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(itemDTO)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("\$[0]", `is`(Name.BlankName.description)))
            .andExpect(jsonPath("\$[1]", `is`(Standard.InvalidQuality.description)))
            .andExpect(jsonPath("\$[2]", `is`(ValidShelfLife.InvalidLifecycle.description)))
    }

    private fun json(itemDTO: ItemDTORequest): String =
        ObjectMapper().writeValueAsString(itemDTO)
}
