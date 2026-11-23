package ru.grigorev.vkeducationprj.domain.model

data class AppDomainModel(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val iconUrl: String?
)
