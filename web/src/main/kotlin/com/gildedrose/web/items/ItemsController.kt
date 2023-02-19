package com.gildedrose.web.items

import com.gildedrose.domain.items.Item
import com.gildedrose.usecases.IGetStock
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/items")
class ItemsController(val getStock: IGetStock) {
    @GetMapping
    fun getItems(): List<ItemDTO> =
        getStock.asOf(LocalDate.now()).map(ItemDTO.Companion::from)

    @Suppress("unused")
    class ItemDTO(
        val name: String,
        val quality: Int,
    ) {
        companion object {
            fun from(item: Item): ItemDTO =
                ItemDTO(
                    item.name.toString(),
                    item.quality.value
                )
        }
    }
}