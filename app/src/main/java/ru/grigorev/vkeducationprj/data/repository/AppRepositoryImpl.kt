package ru.grigorev.vkeducationprj.data.repository

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.grigorev.vkeducationprj.data.model.AppMapper
import ru.grigorev.vkeducationprj.data.network.CatalogApi
import ru.grigorev.vkeducationprj.domain.model.AppDetailsDomainModel
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

// Реализация репозитория. ViewModel знает только интерфейс AppRepository,
// а подставляет эту реализацию Hilt (см. di/AppModule.kt, bindAppRepository).
// CatalogApi приходит из di (data/di/NetworkModule.kt).
class AppRepositoryImpl @Inject constructor(
    private val api: CatalogApi,
    private val mapper: AppMapper
) : AppRepository {

    // Простой кэш каталога в памяти: список грузится с сервера один раз,
    // повторные вызовы (открытие карточки, витрина) берут данные из кэша.
    private var cachedApps: List<AppDomainModel>? = null

    override suspend fun getApps(): List<AppDomainModel> {
        cachedApps?.let { return it }
        val apps = api.getCatalog().map { mapper.map(it) }
        cachedApps = apps
        return apps
    }

    override suspend fun getAppById(id: String): AppDomainModel? {
        return getApps().find { it.id == id }
    }

    // Запрос GET /catalog/{id} реализован по заданию,
    // но в интерфейс пользователя пока не интегрирован.
    override suspend fun getAppDetails(id: String): AppDetailsDomainModel {
        return mapper.mapDetails(api.getAppDetails(id))
    }

    // ДЗ 9, задание 2: ПОСЛЕДОВАТЕЛЬНАЯ загрузка рейтингов.
    // Каждый следующий запрос стартует только после завершения предыдущего,
    // поэтому суммарное время = N * 1500 мс (N — число приложений).
    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val ratings = mutableMapOf<String, Float>()
        for (id in ids) {
            ratings[id] = api.getRating(id)
        }
        return ratings
    }

    // ДЗ 9, задание 2: ПАРАЛЛЕЛЬНАЯ загрузка без ограничений.
    // Все запросы запускаются одновременно: для каждого id создаётся async-корутина,
    // awaitAll() дожидается всех результатов внутри coroutineScope.
    // Суммарное время ≈ самому медленному запросу (~1500 мс), независимо от N.
    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id -> async { id to api.getRating(id) } }
            .awaitAll()
            .toMap()
    }
}
