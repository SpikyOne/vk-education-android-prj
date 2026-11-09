package ru.grigorev.vkeducationprj.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

// Состояние экрана: загрузка / данные / ошибка
sealed interface AppListUiState {
    data object Loading : AppListUiState
    data class Success(val apps: List<AppDomainModel>) : AppListUiState
    data class Error(val message: String) : AppListUiState
}

// Одноразовые события интерфейса. Живут в Channel, а не в состоянии экрана,
// поэтому после поворота экрана Snackbar не показывается повторно.
sealed interface AppUiEvent {
    data class ShowSnackbar(val message: String) : AppUiEvent
}

// ViewModel получает AppRepository (интерфейс), а Hilt подставляет AppRepositoryImpl.
@HiltViewModel
class AppListViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AppListUiState>(AppListUiState.Loading)
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    private val _events = Channel<AppUiEvent>(Channel.BUFFERED)
    val events: Flow<AppUiEvent> = _events.receiveAsFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        _uiState.value = AppListUiState.Loading
        viewModelScope.launch {
            _uiState.value = try {
                AppListUiState.Success(repository.getApps())
            } catch (e: Exception) {
                AppListUiState.Error(e.message ?: "Не удалось загрузить каталог")
            }
        }
    }

    fun onToolbarClick() {
        _events.trySend(AppUiEvent.ShowSnackbar("Привет из ViewModel через DI (Hilt)!"))
    }
}
