package ru.grigorev.vkeducationprj.data.model

// Элемент каталога: GET http://185.103.109.134/catalog
// {
//   "id": "fa2e31b8-1234-4cf7-9914-108a170a1b01",   <- UUID (строка, не Int!)
//   "name": "QuickNote",
//   "description": "Лёгкое приложение для заметок и напоминаний",
//   "category": "Производительность",
//   "iconUrl": "https://..."
// }
data class AppDto(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val iconUrl: String? = null
)
