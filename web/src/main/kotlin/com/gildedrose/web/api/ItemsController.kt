package com.gildedrose.web.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.gildedrose.domain.items.Item
import com.gildedrose.domain.items.ValidationError
import com.gildedrose.usecases.*
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDate
import kotlin.time.DurationUnit

const val ITEMS_PATH = "/api/items"
const val AS_OF_PATH = "/as-of"
const val EMPTY = ""

private fun <E> List<E>.toJSON(): String =
    ObjectMapper().writeValueAsString(this)

@RestController
@RequestMapping(ITEMS_PATH)
class ItemsController(
    val getStock: IGetStock,
    val addItemToStock: IAddItemToStock,
    val deleteItemFromStock: IDeleteItemFromStock
) {
    @GetMapping
    fun getItems(): List<ItemDTOResponse> =
        getItemsAsOf(LocalDate.now())

    @GetMapping("$AS_OF_PATH/{date}")
    fun getItemsAsOf(@PathVariable date: LocalDate): List<ItemDTOResponse> =
        getStock.asOf(date).map(ItemDTOResponse::fromItem)

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun createItem(@RequestBody itemDTO: ItemDTORequest): ResponseEntity<String> =
        addItemToStock.addItem(itemDTO).fold(
            { validationErrors ->
                ResponseEntity
                    .unprocessableEntity()
                    .body(validationErrors.map(ValidationError::description).toJSON())
            },
            { _ ->
                ResponseEntity
                    .created(URI(ITEMS_PATH))
                    .body(EMPTY)
            }
        )

    @Hidden
    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Long): ResponseEntity<Nothing> {
        deleteItemFromStock.deleteById(ItemId.of(id)!!)
        return ResponseEntity
            .noContent()
            .build()
    }
}

data class ItemDTORequest(
    override val name: String,
    override val quality: Int,
    override val registeredOn: String,
    override val sellBy: String,
) : ItemDTO

@Suppress("unused")
class ItemDTOResponse(
    val name: String,
    val quality: Int,
    val sellIn: Int,
) {
    companion object {
        fun fromItem(item: Item): ItemDTOResponse =
            ItemDTOResponse(
                item.name.toString(),
                item.quality.value,
                item.sellIn.toInt(DurationUnit.DAYS)
            )
    }
}
