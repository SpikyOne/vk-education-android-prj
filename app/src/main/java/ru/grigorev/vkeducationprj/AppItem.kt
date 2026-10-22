package ru.grigorev.vkeducationprj

import androidx.annotation.DrawableRes

data class AppInfo(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    @param:DrawableRes val iconRes: Int
)

val sampleAppList = listOf(
    AppInfo(1, "ВКонтакте", "Социальные сети", "Общение, музыка, видео и сервисы", R.drawable.ic_launcher_foreground),
    AppInfo(2, "VK Видео", "Развлечения", "Тысячи фильмов, сериалов и шоу", R.drawable.ic_launcher_foreground),
    AppInfo(3, "RuStore", "Инструменты", "Официальный российский магазин приложений", R.drawable.ic_launcher_foreground),
    AppInfo(4, "VK Музыка", "Музыка и аудио", "Миллионы треков и подкастов", R.drawable.ic_launcher_foreground),
    AppInfo(5, "Почта Mail.ru", "Продуктивность", "Быстрый почтовый клиент", R.drawable.ic_launcher_foreground),
    AppInfo(6, "Одноклассники", "Социальные сети", "Социальная сеть для общения с близкими", R.drawable.ic_launcher_foreground),
    AppInfo(7, "VK Клипы", "Развлечения", "Короткие вертикальные видео", R.drawable.ic_launcher_foreground),
    AppInfo(8, "Дзен", "Новости", "Статьи, видео и новости по интересам", R.drawable.ic_launcher_foreground),
    AppInfo(9, "Маруся", "Инструменты", "Голосовой помощник от VK", R.drawable.ic_launcher_foreground)
)
