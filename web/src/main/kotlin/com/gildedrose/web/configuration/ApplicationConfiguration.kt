package com.gildedrose.web.configuration

import com.gildedrose.repositories.IStockRepository
import com.gildedrose.usecases.AddItemToStock
import com.gildedrose.usecases.GetStock
import com.gildedrose.usecases.IAddItemToStock
import com.gildedrose.usecases.IGetStock
import com.gildedrose.web.repositories.InMemoryStockRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {
    @Bean
    fun stockRepository(): IStockRepository =
        InMemoryStockRepository()

    @Bean
    fun getStock(stockRepository: IStockRepository): IGetStock =
        GetStock(stockRepository)

    @Bean
    fun addItemToStock(stockRepository: IStockRepository): IAddItemToStock =
        AddItemToStock(stockRepository)
}