package com.gildedrose.web.infra.configuration

import com.gildedrose.repositories.IStockRepository
import com.gildedrose.usecases.*
import com.gildedrose.web.infra.repositories.InMemoryStockRepository
import com.gildedrose.web.infra.repositories.StockRepository
import com.gildedrose.web.infra.repositories.StockRepositoryAdapter
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

    @Bean
    fun deleteItemFromStock(): IDeleteItemFromStock =
        DeleteItemFromStock()
}