package ru.grigorev.vkeducationprj


import androidx.annotation.DrawableRes




data class AppInfo(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    @param:DrawableRes val iconRes: Int
)

// Захардкоженный список приложений для демонстрации
val sampleAppList = listOf(

    AppInfo(
        id = 1,
        name = "ВКонтакте",
        category = "Социальные сети",
        description = "Общение, музыка, видео и сервисы на каждый день",
        iconRes = R.drawable.ic_launcher_foreground
    ),

    AppInfo(
        id = 2,
        name = "VK Видео",
        category = "Развлечения",
        description = "Тысячи фильмов, сериалов и эксклюзивных шоу",
        iconRes = R.drawable.ic_launcher_foreground
    ),

    AppInfo(
        id = 3,
        name = "RuStore",
        category = "Инструменты",
        description = "Официальный российский магазин приложений для Android",
        iconRes = R.drawable.ic_launcher_foreground
    ),

    AppInfo(
        id = 4,
        name = "VK Музыка",
        category = "Музыка и аудио",
        description = "Миллионы треков, подкасты и радио без ограничений",
        iconRes = R.drawable.ic_launcher_foreground
    ),

    AppInfo(
        id = 5,
        name = "Почта Mail.ru",
        category = "Продуктивность",
        description = "Быстрый и надежный почтовый клиент для работы и учебы",
        iconRes = R.drawable.ic_launcher_foreground
    )

)
