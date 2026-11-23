package ru.grigorev.vkeducationprj.domain.repository

import ru.grigorev.vkeducationprj.domain.model.AppDetailsDomainModel
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel

interface AppRepository {
    suspend fun getApps(): List<AppDomainModel>
    suspend fun getAppById(id: String): AppDomainModel?
    suspend fun getAppDetails(id: String): AppDetailsDomainModel

    // ДЗ 9: два способа загрузки рейтингов — для сравнения времени выполнения.
    // Возвращают Map: id приложения -> его рейтинг.
    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float>
    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float>
}
