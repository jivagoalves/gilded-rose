package com.gildedrose.domain.items

import arrow.core.Validated
import arrow.core.zip
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.lifecycle.ValidShelfLife
import com.gildedrose.usecases.ItemDTO

interface ValidationError {
    val description: String
}

object Validation {

    fun validate(itemDTO: ItemDTO): Validated<List<ValidationError>, ValidItem> =
        Name.validatedFrom(itemDTO.name)
            .zip(
                StandardQuality.validatedFrom(itemDTO.quality),
                ValidShelfLife.validatedFrom(itemDTO.shelfLife)
            ) { name, quality, validShelfLife ->
                ValidItem(
                    name,
                    JustValid(validShelfLife),
                    quality
                )
            }

}