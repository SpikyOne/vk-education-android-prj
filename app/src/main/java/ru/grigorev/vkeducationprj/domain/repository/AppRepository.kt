package ru.grigorev.vkeducationprj.domain.repository

import ru.grigorev.vkeducationprj.domain.model.AppDetailsDomainModel
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel

interface AppRepository {
    suspend fun getApps(): List<AppDomainModel>
    suspend fun getAppById(id: String): AppDomainModel?
    suspend fun getAppDetails(id: String): AppDetailsDomainModel
}
