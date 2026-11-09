package ru.grigorev.vkeducationprj.data.repository

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
}
