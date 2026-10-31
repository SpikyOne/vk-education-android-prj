package ru.grigorev.vkeducationprj.presentation.list // (или твой пакет presentation)

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.grigorev.vkeducationprj.data.repository.AppRepositoryImpl
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository

data class AppUiState(
    val apps: List<AppDomainModel> = emptyList(),
    val showSnackbar: Boolean = false,
    val snackbarMessage: String = ""
)

class AppListViewModel(
    private val repository: AppRepository = AppRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        _uiState.update { it.copy(apps = repository.getApps()) }
    }

    fun onToolbarClick() {
        _uiState.update {
            it.copy(
                showSnackbar = true,
                snackbarMessage = "Привет из ViewModel через Clean Architecture!"
            )
        }
    }

    fun onSnackbarShown() {
        _uiState.update {
            it.copy(showSnackbar = false)
        }
    }
}
