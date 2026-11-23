package ru.grigorev.vkeducationprj.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

sealed interface AppDetailsUiState {
    data object Loading : AppDetailsUiState
    data class Success(
        val app: AppDomainModel,
        val showcaseApps: List<AppDomainModel>,
        // ДЗ 9, задание 1: состояние установки приложения на карточке.
        val installState: InstallState = InstallState.NotInstalled
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

    // Джоба эмуляции установки. Хранится, чтобы её можно было отменить по клику «Отменить».
    private var installJob: Job? = null

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

    // ДЗ 9, задание 1: кнопка «Установить» / «Отменить».
    fun onInstallClick() {
        val current = _uiState.value
        if (current !is AppDetailsUiState.Success) return

        if (current.installState is InstallState.Installing) {
            // Установка идёт — отменяем джобу и возвращаем состояние в NotInstalled.
            installJob?.cancel()
            installJob = null
            _uiState.update { state ->
                if (state is AppDetailsUiState.Success) {
                    state.copy(installState = InstallState.NotInstalled)
                } else {
                    state
                }
            }
        } else {
            // Запускаем эмуляцию установки: прогресс 0..100 в отдельной корутине.
            installJob = viewModelScope.launch { emulateInstall() }
        }
    }

    // Эмуляция установки: шаг 5% каждые 150 мс, в конце — Installed.
    // Пока джоба выполняется, кнопка показывает «Отменить».
    // Если джобу отменили, цикл прервётся на delay и Installed выставлен не будет.
    private suspend fun emulateInstall() {
        for (progress in 0..100 step 5) {
            delay(150)
            _uiState.update { state ->
                if (state is AppDetailsUiState.Success) {
                    state.copy(installState = InstallState.Installing(progress))
                } else {
                    state
                }
            }
        }
        _uiState.update { state ->
            if (state is AppDetailsUiState.Success) {
                state.copy(installState = InstallState.Installed)
            } else {
                state
            }
        }
    }
}
