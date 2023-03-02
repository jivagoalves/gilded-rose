package com.gildedrose.web.infra.repositories

import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.repositories.IStockRepository
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate


interface StockRepository : CrudRepository<ItemEntity, Long>

@Entity
@Table(name = "items")
class ItemEntity(
    var name: String,
    var registeredOn: LocalDate,
    var sellBy: LocalDate,
    var quality: Int,
    @Id @GeneratedValue var id: Long? = null
)

class StockRepositoryAdapter(
    private val stockRepository: StockRepository
) : IStockRepository {

    override fun findAll(): List<ValidItem> {
        return stockRepository.findAll().map {
            ValidItem(
                N(it.name)!!,
                JustValid(Valid(ShelfLife(it.registeredOn, it.sellBy))!!),
                StandardQuality.of(it.quality)!!
            )
        }
    }

    override fun save(validItem: ValidItem) {
        stockRepository.save(
            ItemEntity(
                validItem.name.value,
                validItem.lifecycle.value.registeredOn,
                validItem.lifecycle.value.sellBy,
                validItem.quality.value
            )
        )
    }

}