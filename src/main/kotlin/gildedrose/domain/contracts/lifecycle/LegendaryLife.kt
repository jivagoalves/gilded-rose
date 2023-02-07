package gildedrose.domain.contracts.lifecycle

import java.time.LocalDate

data class LegendaryLife(override val registeredOn: LocalDate): Lifecycle {
    override val sellBy: LocalDate = LocalDate.MAX
}
