package com.gildedrose.web.infra.repositories

import com.gildedrose.domain.StockEntry
import com.gildedrose.domain.contracts.OneOf.JustValid
import com.gildedrose.domain.contracts.Valid
import com.gildedrose.domain.contracts.lifecycle.ShelfLife
import com.gildedrose.domain.items.ItemId
import com.gildedrose.domain.items.N
import com.gildedrose.domain.items.StandardQuality
import com.gildedrose.domain.items.ValidItem
import com.gildedrose.repositories.IStockRepository
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.dao.EmptyResultDataAccessException
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

    override fun findAll(): List<StockEntry> =
        stockRepository.findAll().map {
            StockEntry(
                ItemId.of(it.id!!),
                ValidItem(
                    N(it.name)!!,
                    JustValid(Valid(ShelfLife(it.registeredOn, it.sellBy))!!),
                    StandardQuality.of(it.quality)!!
                )
            )
        }

    override fun save(validItem: ValidItem): StockEntry =
        stockRepository.save(
            ItemEntity(
                validItem.name.value,
                validItem.lifecycle.value.registeredOn,
                validItem.lifecycle.value.sellBy,
                validItem.quality.value
            )
        ).let {
            StockEntry(ItemId.of(it.id!!), validItem)
        }

    override fun deleteById(id: ItemId): Boolean =
        try {
            stockRepository.deleteById(id.value)
            true
        } catch (_: EmptyResultDataAccessException) {
            false
        }

}