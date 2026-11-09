package ru.grigorev.vkeducationprj.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class AppDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository
) : ViewModel() {

    // appId приходит из маршрута навигации app_details/{appId}
    private val appId: Int = checkNotNull(savedStateHandle["appId"])

    val app: AppDomainModel = repository.getAppById(appId) ?: repository.getApps().first()

    // Список всех приложений для блока «Витрина» (3 страницы по 3 приложения)
    val showcaseApps: List<AppDomainModel> = repository.getApps()
}
