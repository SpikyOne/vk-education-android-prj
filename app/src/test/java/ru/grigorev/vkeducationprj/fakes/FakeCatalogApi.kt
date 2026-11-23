package ru.grigorev.vkeducationprj.fakes

import kotlinx.coroutines.delay
import ru.grigorev.vkeducationprj.data.model.AppDetailsDto
import ru.grigorev.vkeducationprj.data.model.AppDto
import ru.grigorev.vkeducationprj.data.network.CatalogApi

// Подмена CatalogApi для юнит-тестов: работает без сети.
// ratingDelayMillis эмулирует сетевую задержку — в runTest виртуальное время
// проматывается мгновенно, поэтому тесты остаются быстрыми.
class FakeCatalogApi(
    private val catalog: List<AppDto> = emptyList(),
    private val details: Map<String, AppDetailsDto> = emptyMap(),
    private val ratingValue: Float = 4.5f,
    private val ratingDelayMillis: Long = 0
) : CatalogApi {

    var getCatalogCallCount = 0
        private set

    override suspend fun getCatalog(): List<AppDto> {
        getCatalogCallCount++
        return catalog
    }

    override suspend fun getAppDetails(id: String): AppDetailsDto =
        details.getValue(id)

    override suspend fun getRating(id: String): Float {
        if (ratingDelayMillis > 0) delay(ratingDelayMillis)
        return ratingValue
    }
}
