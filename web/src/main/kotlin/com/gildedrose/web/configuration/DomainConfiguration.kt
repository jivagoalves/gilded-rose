package com.gildedrose.web.configuration

import com.gildedrose.usecases.GetStock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainConfiguration {
    @Bean
    fun getStock(): GetStock =
        GetStock
}