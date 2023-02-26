package com.gildedrose.web.configuration

import com.gildedrose.repositories.IStockRepository
import com.gildedrose.usecases.AddItemToStock
import com.gildedrose.usecases.GetStock
import com.gildedrose.usecases.IAddItemToStock
import com.gildedrose.usecases.IGetStock
import com.gildedrose.web.repositories.InMemoryStockRepository
import com.gildedrose.web.repositories.StockRepository
import com.gildedrose.web.repositories.StockRepositoryAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class ApplicationConfiguration {
    @Bean
    @Primary
    fun stockRepositoryAdapter(stockRepository: StockRepository): IStockRepository =
        StockRepositoryAdapter(stockRepository)

    @Bean
    fun inMemoryStockRepository(): IStockRepository =
        InMemoryStockRepository()

    @Bean
    fun getStock(stockRepository: IStockRepository): IGetStock =
        GetStock(stockRepository)

    @Bean
    fun addItemToStock(stockRepository: IStockRepository): IAddItemToStock =
        AddItemToStock(stockRepository)
}