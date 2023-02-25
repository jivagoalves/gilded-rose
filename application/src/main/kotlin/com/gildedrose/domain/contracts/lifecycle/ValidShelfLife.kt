package com.gildedrose.domain.contracts.lifecycle

import arrow.core.Nel
import arrow.core.Validated
import arrow.core.invalidNel
import arrow.core.valid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.items.ValidationError
import com.gildedrose.usecases.ItemDTO
import java.time.LocalDate
import java.time.format.DateTimeParseException

object ValidShelfLife {

    fun validatedFrom(
        shelfLife: ItemDTO.ShelfLifeDTO
    ): Validated<Nel<InvalidLifecycle>, Valid<ShelfLife>> =
        try {
            val (registeredOn, sellBy) = shelfLife
            Valid(ShelfLife(LocalDate.parse(registeredOn), LocalDate.parse(sellBy)))
                ?.valid()
                ?: InvalidLifecycle.invalidNel()
        } catch (_: DateTimeParseException) {
            InvalidLifecycle.invalidNel()
        }

    object InvalidLifecycle : ValidationError {
        override val description = "Lifecycle is not valid"

        override fun toString(): String = "InvalidLifecycle"
    }

}