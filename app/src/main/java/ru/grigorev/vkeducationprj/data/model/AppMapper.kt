package ru.grigorev.vkeducationprj.data.model

import ru.grigorev.vkeducationprj.domain.model.AppDetailsDomainModel
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel

// Маппер DTO -> Domain. Создаётся через DI (см. di/AppModule.kt, provideAppMapper).
class AppMapper {

    fun map(dto: AppDto): AppDomainModel {
        return AppDomainModel(
            id = dto.id,
            name = dto.name,
            category = dto.category,
            description = dto.description,
            iconUrl = dto.iconUrl
        )
    }

    fun mapDetails(dto: AppDetailsDto): AppDetailsDomainModel {
        return AppDetailsDomainModel(
            id = dto.id,
            name = dto.name,
            developer = dto.developer,
            category = dto.category,
            ageRating = dto.ageRating,
            size = dto.size,
            iconUrl = dto.iconUrl,
            screenshotUrlList = dto.screenshotUrlList,
            description = dto.description
        )
    }

    // Обратные преобразования Domain -> DTO (ДЗ 10: мапперы в обе стороны —
    // пригодятся, когда появится отправка данных на сервер).
    fun mapToDto(domain: AppDomainModel): AppDto {
        return AppDto(
            id = domain.id,
            name = domain.name,
            category = domain.category,
            description = domain.description,
            iconUrl = domain.iconUrl
        )
    }

    fun mapDetailsToDto(domain: AppDetailsDomainModel): AppDetailsDto {
        return AppDetailsDto(
            id = domain.id,
            name = domain.name,
            developer = domain.developer,
            category = domain.category,
            ageRating = domain.ageRating,
            size = domain.size,
            iconUrl = domain.iconUrl,
            screenshotUrlList = domain.screenshotUrlList,
            description = domain.description
        )
    }
}
