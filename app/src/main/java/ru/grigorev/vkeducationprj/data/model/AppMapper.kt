package ru.grigorev.vkeducationprj.data.model

import ru.grigorev.vkeducationprj.domain.model.AppDomainModel

fun AppDto.toDomain(): AppDomainModel {
    return AppDomainModel(
        id = id,
        name = name,
        category = category,
        description = description,
        iconRes = iconRes
    )
}
