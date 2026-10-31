package ru.grigorev.vkeducationprj.domain.repository

import ru.grigorev.vkeducationprj.domain.model.AppDomainModel

interface AppRepository {
    fun getApps(): List<AppDomainModel>
    fun getAppById(id: Int): AppDomainModel?
}
