package ru.grigorev.vkeducationprj.data.model

// Ответ GET http://185.103.109.134/catalog/{id} — подробная информация о приложении.
// По заданию этот эндпоинт интегрировать в UI пока не нужно,
// поэтому метод репозитория getAppDetails() реализован, но пока никем не вызывается.
data class AppDetailsDto(
    val id: String,
    val name: String,
    val developer: String? = null,
    val category: String? = null,
    val ageRating: Int? = null,
    val size: Double? = null,
    val iconUrl: String? = null,
    val screenshotUrlList: List<String> = emptyList(),
    val description: String? = null
)
