package ru.grigorev.vkeducationprj.fakes

import ru.grigorev.vkeducationprj.domain.model.AppDetailsDomainModel
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository

// Подмена репозитория для тестов юзкейсов: считает вызовы
// и запоминает, с какими аргументами его вызвали.
class FakeAppRepository(
    private val apps: List<AppDomainModel> = emptyList(),
    private val ratings: Map<String, Float> = emptyMap()
) : AppRepository {

    var getAppsCallCount = 0
        private set

    var lastSequentialIds: List<String>? = null
        private set

    var lastParallelIds: List<String>? = null
        private set

    override suspend fun getApps(): List<AppDomainModel> {
        getAppsCallCount++
        return apps
    }

    override suspend fun getAppById(id: String): AppDomainModel? =
        apps.find { it.id == id }

    override suspend fun getAppDetails(id: String): AppDetailsDomainModel =
        throw NotImplementedError("getAppDetails не используется в тестах юзкейсов")

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        lastSequentialIds = ids
        return ratings
    }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> {
        lastParallelIds = ids
        return ratings
    }
}
