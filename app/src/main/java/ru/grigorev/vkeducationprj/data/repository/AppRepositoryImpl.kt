package ru.grigorev.vkeducationprj.data.repository

import ru.grigorev.vkeducationprj.R
import ru.grigorev.vkeducationprj.data.model.AppDto
import ru.grigorev.vkeducationprj.data.model.toDomain
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository

class AppRepositoryImpl : AppRepository {
    private val localDtos = listOf(
        AppDto(1, "ВКонтакте", "Социальные сети", "Общение, музыка, видео и сервисы", R.drawable.ic_launcher_foreground),
        AppDto(2, "VK Видео", "Развлечения", "Тысячи фильмов, сериалов и шоу", R.drawable.ic_launcher_foreground),
        AppDto(3, "RuStore", "Инструменты", "Официальный российский магазин приложений", R.drawable.ic_launcher_foreground),
        AppDto(4, "VK Музыка", "Музыка и аудио", "Миллионы треков и подкастов", R.drawable.ic_launcher_foreground),
        AppDto(5, "Почта Mail.ru", "Продуктивность", "Быстрый почтовый клиент", R.drawable.ic_launcher_foreground),
        AppDto(6, "Одноклассники", "Социальные сети", "Социальная сеть для общения с близкими", R.drawable.ic_launcher_foreground),
        AppDto(7, "VK Клипы", "Развлечения", "Короткие вертикальные видео", R.drawable.ic_launcher_foreground),
        AppDto(8, "Дзен", "Новости", "Статьи, видео и новости по интересам", R.drawable.ic_launcher_foreground),
        AppDto(9, "Маруся", "Инструменты", "Голосовой помощник от VK", R.drawable.ic_launcher_foreground)
    )

    override fun getApps(): List<AppDomainModel> {
        return localDtos.map { it.toDomain() }
    }

    override fun getAppById(id: Int): AppDomainModel? {
        return localDtos.find { it.id == id }?.toDomain()
    }
}
