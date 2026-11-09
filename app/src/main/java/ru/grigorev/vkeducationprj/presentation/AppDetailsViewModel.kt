package ru.grigorev.vkeducationprj.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

sealed interface AppDetailsUiState {
    data object Loading : AppDetailsUiState
    data class Success(
        val app: AppDomainModel,
        val showcaseApps: List<AppDomainModel>
    ) : AppDetailsUiState
    data class Error(val message: String) : AppDetailsUiState
}

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository
) : ViewModel() {

    // appId приходит из маршрута навигации app_details/{appId}
    private val appId: String = checkNotNull(savedStateHandle["appId"])

    private val _uiState = MutableStateFlow<AppDetailsUiState>(AppDetailsUiState.Loading)
    val uiState: StateFlow<AppDetailsUiState> = _uiState.asStateFlow()

    init {
        loadApp()
    }

    fun loadApp() {
        _uiState.value = AppDetailsUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                // getAppById и getApps берут каталог из кэша репозитория,
                // поэтому лишнего запроса в сеть не будет
                val app = repository.getAppById(appId)
                    ?: throw IllegalStateException("Приложение с id=$appId не найдено")
                AppDetailsUiState.Success(app, repository.getApps())
            } catch (e: Exception) {
                AppDetailsUiState.Error(e.message ?: "Не удалось загрузить данные")
            }
        }
    }
}
