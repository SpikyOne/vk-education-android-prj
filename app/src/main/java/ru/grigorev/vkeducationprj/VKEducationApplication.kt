package ru.grigorev.vkeducationprj

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Точка входа DI: Hilt генерирует код и создаёт граф зависимостей здесь.
// Класс указан в AndroidManifest.xml (android:name=".VKEducationApplication").
@HiltAndroidApp
class VKEducationApplication : Application()
