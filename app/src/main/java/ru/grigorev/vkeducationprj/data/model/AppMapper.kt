package ru.grigorev.vkeducationprj.data.model

import ru.grigorev.vkeducationprj.domain.model.AppDomainModel

// Маппер DTO -> Domain. Создаётся через DI (см. di/AppModule.kt, provideAppMapper).
class AppMapper {

    fun map(dto: AppDto): AppDomainModel {
        return AppDomainModel(
            id = dto.id,
            name = dto.name,
            category = dto.category,
            description = dto.description,
            iconRes = dto.iconRes
        )
    }
}
