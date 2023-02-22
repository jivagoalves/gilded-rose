package com.gildedrose.usecases

import com.gildedrose.domain.Stock
import com.gildedrose.repositories.IStockRepository
import java.time.LocalDate

interface IGetStock {
    fun asOf(date: LocalDate): Stock
}

class GetStock(private val stockRepository: IStockRepository) : IGetStock {
    override fun asOf(date: LocalDate): Stock =
        Stock.of(stockRepository.findAll().map { it.asOf(date) })
}