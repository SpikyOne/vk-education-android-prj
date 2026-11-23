package ru.grigorev.vkeducationprj.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject
import kotlin.system.measureTimeMillis

// Состояние экрана: загрузка / данные / ошибка
sealed interface AppListUiState {
    data object Loading : AppListUiState
    data class Success(
        val apps: List<AppDomainModel>,
        // ДЗ 9, задание 2: загруженные рейтинги (id -> рейтинг).
        val ratings: Map<String, Float> = emptyMap(),
        // Идёт загрузка рейтингов — кнопки способов заблокированы, показывается индикатор.
        val ratingsLoading: Boolean = false
    ) : AppListUiState
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

    // Для замера берём первые 10 приложений: в каталоге с сервера 41 элемент,
    // последовательная загрузка всех заняла бы ~61 с (41 * 1500 мс).
    // Задание требует замер на списке из 10+ приложений.
    private companion object {
        const val RATINGS_SAMPLE_SIZE = 10
    }

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

    // ДЗ 9, задание 2: последовательная загрузка — каждый рейтинг запрашивается
    // только после предыдущего. Время замеряется через measureTimeMillis.
    fun loadRatingsSequentially() {
        loadRatings("Последовательно") { ids -> repository.loadRatingsSequentially(ids) }
    }

    // ДЗ 9, задание 2: параллельная загрузка — все запросы одновременно (async + awaitAll).
    fun loadRatingsInParallel() {
        loadRatings("Параллельно") { ids -> repository.loadRatingsInParallel(ids) }
    }

    private fun loadRatings(
        mode: String,
        loader: suspend (List<String>) -> Map<String, Float>
    ) {
        val current = _uiState.value as? AppListUiState.Success ?: return
        if (current.ratingsLoading) return // не запускаем вторую загрузку поверх идущей

        val ids = current.apps.take(RATINGS_SAMPLE_SIZE).map { it.id }

        viewModelScope.launch {
            _uiState.update { state ->
                if (state is AppListUiState.Success) state.copy(ratingsLoading = true) else state
            }
            try {
                val elapsedMillis = measureTimeMillis {
                    val ratings = loader(ids)
                    _uiState.update { state ->
                        if (state is AppListUiState.Success) state.copy(ratings = ratings) else state
                    }
                }
                _events.trySend(
                    AppUiEvent.ShowSnackbar("$mode ($RATINGS_SAMPLE_SIZE приложений): $elapsedMillis мс")
                )
            } catch (e: Exception) {
                _events.trySend(
                    AppUiEvent.ShowSnackbar("Ошибка загрузки рейтингов: ${e.message}")
                )
            } finally {
                _uiState.update { state ->
                    if (state is AppListUiState.Success) state.copy(ratingsLoading = false) else state
                }
            }
        }
    }
}
