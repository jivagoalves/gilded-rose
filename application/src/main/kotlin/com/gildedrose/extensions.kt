package com.gildedrose

import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

internal val Int.day: Duration
    get() = this.days

internal operator fun LocalDate.minus(days: Duration): LocalDate =
    minusDays(days.toLong(DurationUnit.DAYS))

internal operator fun LocalDate.plus(days: Duration): LocalDate =
    if (this == LocalDate.MAX) LocalDate.MAX
    else this.plusDays(days.toLong(DurationUnit.DAYS))
