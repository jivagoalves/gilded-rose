package com.gildedrose

import com.gildedrose.domain.Stock
import java.io.OutputStream

class Simulation(private val stock: Stock, out: OutputStream = System.out) {
    private val writer = out.bufferedWriter()

    fun run(iterations: Int) {
        generateSequence(stock, Stock::age)
            .take(iterations)
            .forEachIndexed { day, agedItems ->
                writer.appendLine("== Day $day ==")
                writer.appendLine("")
                agedItems.forEach { writer.appendLine(it.toString()) }
                writer.appendLine("")
            }
        writer.flush()
    }
}

