package ru.grigorev.vkeducationprj.fakes

import ru.grigorev.vkeducationprj.data.model.AppDetailsDto
import ru.grigorev.vkeducationprj.data.model.AppDto
import ru.grigorev.vkeducationprj.domain.model.AppDetailsDomainModel
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel

// Общие тестовые данные для юнит-тестов (ДЗ 10).
object TestData {

    const val ID_1 = "11111111-1111-1111-1111-111111111111"
    const val ID_2 = "22222222-2222-2222-2222-222222222222"

    val dto1 = AppDto(
        id = ID_1,
        name = "QuickNote",
        category = "Производительность",
        description = "Лёгкое приложение для заметок",
        iconUrl = "https://example.com/icon1.png"
    )

    val dto2 = AppDto(
        id = ID_2,
        name = "FitRiver",
        category = "Здоровье и фитнес",
        description = "Трекер прогулок и пробежек",
        iconUrl = "https://example.com/icon2.png"
    )

    val catalog = listOf(dto1, dto2)

    val domain1 = AppDomainModel(
        id = ID_1,
        name = "QuickNote",
        category = "Производительность",
        description = "Лёгкое приложение для заметок",
        iconUrl = "https://example.com/icon1.png"
    )

    val domain2 = AppDomainModel(
        id = ID_2,
        name = "FitRiver",
        category = "Здоровье и фитнес",
        description = "Трекер прогулок и пробежек",
        iconUrl = "https://example.com/icon2.png"
    )

    val domainCatalog = listOf(domain1, domain2)

    val detailsDto = AppDetailsDto(
        id = ID_1,
        name = "QuickNote",
        developer = "QuickSoft Inc.",
        category = "Производительность",
        ageRating = 3,
        size = 8.2,
        iconUrl = "https://example.com/icon1.png",
        screenshotUrlList = listOf("https://example.com/s1.png", "https://example.com/s2.png"),
        description = "Лёгкое приложение для заметок"
    )

    val detailsDomain = AppDetailsDomainModel(
        id = ID_1,
        name = "QuickNote",
        developer = "QuickSoft Inc.",
        category = "Производительность",
        ageRating = 3,
        size = 8.2,
        iconUrl = "https://example.com/icon1.png",
        screenshotUrlList = listOf("https://example.com/s1.png", "https://example.com/s2.png"),
        description = "Лёгкое приложение для заметок"
    )

    // Список из 10 id — как в замере ДЗ 9 (список из 10+ приложений).
    val ids10: List<String> = (1..10).map { "id-$it" }
}
