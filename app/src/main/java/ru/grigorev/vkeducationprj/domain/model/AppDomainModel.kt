package ru.grigorev.vkeducationprj.domain.model

import androidx.annotation.DrawableRes

data class AppDomainModel(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    @DrawableRes val iconRes: Int
)
