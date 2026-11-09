package ru.grigorev.vkeducationprj.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

data class AppUiState(
    val apps: List<AppDomainModel> = emptyList()
)

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

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private val _events = Channel<AppUiEvent>(Channel.BUFFERED)
    val events: Flow<AppUiEvent> = _events.receiveAsFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        _uiState.update { it.copy(apps = repository.getApps()) }
    }

    fun onToolbarClick() {
        _events.trySend(AppUiEvent.ShowSnackbar("Привет из ViewModel через DI (Hilt)!"))
    }
}
