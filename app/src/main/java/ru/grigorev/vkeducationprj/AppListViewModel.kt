package ru.grigorev.vkeducationprj

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update



// Состояние экрана (UI State)
data class AppUiState(
    val apps: List<AppInfo> = sampleAppList,
    val showSnackbar: Boolean = false,
    val snackbarMessage: String = ""
)


// ViewModel для управления логикой и состоянием
class AppListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    // Вызывается при клике по тулбару
    fun onToolbarClick() {
        _uiState.update {
            it.copy(
                showSnackbar = true,
                snackbarMessage = "Привет из ViewModel! Тулбар нажат."
            )
        }
    }

    // Сбрасывает флаг показа, чтобы при повороте экрана Snackbar не вылезал повторно
    fun onSnackbarShown() {
        _uiState.update {
            it.copy(showSnackbar = false)
        }
    }
}
