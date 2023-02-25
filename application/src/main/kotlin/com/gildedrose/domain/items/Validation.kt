package com.gildedrose.domain.items

import arrow.core.Validated
import arrow.core.zip
import com.gildedrose.domain.Name
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.lifecycle.ValidShelfLife
import com.gildedrose.domain.quality.Standard
import com.gildedrose.usecases.ItemDTO

object Validation {

    fun validate(itemDTO: ItemDTO): Validated<List<ValidationError>, ValidItem> =
        Name.validatedFrom(itemDTO.name)
            .zip(
                Standard.validatedFrom(itemDTO.quality),
                ValidShelfLife.validatedFrom(itemDTO.shelfLife)
            ) { name, quality, validShelfLife ->
                ValidItem(
                    name,
                    JustValid(validShelfLife),
                    quality
                )
            }

}