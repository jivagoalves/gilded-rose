package com.gildedrose.domain.contracts.lifecycle

import com.gildedrose.domain.contracts.Expirable
import com.gildedrose.domain.contracts.Validatable
import com.gildedrose.plus
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

interface Lifecycle : Validatable, Expirable {
    val registeredOn: LocalDate
    val sellBy: LocalDate

    override val isValid: Boolean
        get() = !isExpired

    override val isExpired: Boolean
        get() = registeredOn > sellBy

    val sellIn: Duration
        get() = when {
            isExpired -> -daysOfPeriod.days
            else -> daysOfPeriod.days
        }

    private val daysOfPeriod: Long
        get() = if (!isExpired)
            ChronoUnit.DAYS.between(registeredOn, sellBy + 1.days)
        else
            ChronoUnit.DAYS.between(sellBy + 1.days, registeredOn)

}