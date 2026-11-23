package ru.grigorev.vkeducationprj.presentation

// ДЗ 9, задание 1: состояние установки приложения.
sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Installing(val progress: Int) : InstallState // 0..100
    data object Installed : InstallState
}
