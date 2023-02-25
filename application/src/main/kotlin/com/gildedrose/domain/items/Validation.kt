package com.gildedrose.domain.items

import arrow.core.Validated
import arrow.core.invalidNel
import arrow.core.valid
import arrow.core.zip
import com.gildedrose.domain.N
import com.gildedrose.domain.Name
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ValidationError.*
import com.gildedrose.domain.quality.Standard
import com.gildedrose.usecases.ItemDTO
import java.time.LocalDate
import java.time.format.DateTimeParseException

object Validation {
    fun validate(itemDTO: ItemDTO): Validated<List<ValidationError>, ValidItem> {

        val vName = when(val name = N(itemDTO.name)) {
            is Name -> name.valid()
            else -> BlankName.invalidNel()
        }

        val vQuality = when(val quality = Standard.of(itemDTO.quality)) {
            is Standard -> quality.valid()
            else -> NegativeQuality.invalidNel()
        }

        val vShelfLife = try {
            Valid(
                ShelfLife(
                    LocalDate.parse(itemDTO.registeredOn),
                    LocalDate.parse(itemDTO.sellBy)
                )
            )?.valid() ?: InvalidLifecycle.invalidNel()
        } catch (_: DateTimeParseException) {
            InvalidLifecycle.invalidNel()
        }

        return vName.zip(
            vQuality,
            vShelfLife
        ) { name, quality, validShelfLife ->
            ValidItem(
                name,
                JustValid(validShelfLife),
                quality
            )
        }
    }
}