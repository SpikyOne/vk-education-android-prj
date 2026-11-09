package ru.grigorev.vkeducationprj.data.model

import androidx.annotation.DrawableRes

data class AppDto(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    @DrawableRes val iconRes: Int
)
