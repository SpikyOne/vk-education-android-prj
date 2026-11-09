package ru.grigorev.vkeducationprj.domain.model

// Доменная модель подробной информации о приложении (GET /catalog/{id}).
data class AppDetailsDomainModel(
    val id: String,
    val name: String,
    val developer: String?,
    val category: String?,
    val ageRating: Int?,
    val size: Double?,
    val iconUrl: String?,
    val screenshotUrlList: List<String>,
    val description: String?
)
