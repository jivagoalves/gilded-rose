package com.gildedrose.web.items

import com.gildedrose.domain.N
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.Item
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.domain.quality.Standard
import com.gildedrose.usecases.IAddItemToStock
import com.gildedrose.usecases.IGetStock
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDate
import kotlin.time.DurationUnit

const val ITEMS_PATH = "/items"

@RestController
@RequestMapping(ITEMS_PATH)
class ItemsController(
    val getStock: IGetStock,
    val addItemToStock: IAddItemToStock
) {
    @GetMapping
    fun getItems(): List<ItemResponseDTO> =
        getStock.asOf(LocalDate.now()).map(ItemResponseDTO::fromItem)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createItem(@RequestBody itemDTO: ItemRequestDTO): ResponseEntity<Nothing> {
        addItemToStock.addItem(itemDTO.toValidItem())
        return ResponseEntity
            .created(URI("/"))
            .body(null)
    }
}

@Suppress("unused", "MemberVisibilityCanBePrivate")
class ItemRequestDTO(
    val name: String,
    val quality: Int,
    val registeredOn: String,
    val sellBy: String,
) {
    fun toValidItem(): ValidItem {
        val name = N(name)!!
        val quality = Standard.of(quality)!!
        val registeredOn = LocalDate.parse(registeredOn)
        val sellBy = LocalDate.parse(sellBy)
        val lifecycle = Valid(ShelfLife(registeredOn, sellBy))!!
        return ValidItem(name, JustValid(lifecycle), quality)
    }

    companion object {
        fun fromItem(item: Item): ItemResponseDTO =
            ItemResponseDTO(
                item.name.toString(),
                item.quality.value,
                item.sellIn.toInt(DurationUnit.DAYS)
            )
    }
}

@Suppress("unused")
class ItemResponseDTO(
    val name: String,
    val quality: Int,
    val sellIn: Int,
) {
    companion object {
        fun fromItem(item: Item): ItemResponseDTO =
            ItemResponseDTO(
                item.name.toString(),
                item.quality.value,
                item.sellIn.toInt(DurationUnit.DAYS)
            )
    }
}
