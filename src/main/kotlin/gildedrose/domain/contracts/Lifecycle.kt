package gildedrose.domain.contracts

import java.time.LocalDate
import java.time.Period
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

interface Lifecycle: Validatable {
    val registeredOn: LocalDate
    val sellBy: LocalDate

    override val isValid: Boolean
        get() = !isExpired

    val isExpired: Boolean
        get() = registeredOn > sellBy

    val sellIn: Duration
        get() = when {
            isExpired -> -daysOfPeriod.days
            else -> daysOfPeriod.days
        }

    private val daysOfPeriod: Int
        get() = period.days

    private val period: Period
        get() = if (!isExpired)
            Period.between(registeredOn, sellBy)
        else
            Period.between(sellBy, registeredOn)
}
