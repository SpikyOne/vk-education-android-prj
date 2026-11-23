package ru.grigorev.vkeducationprj.domain.usecase

import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

// Юзкейс: получить каталог приложений.
// Вызывается как функция благодаря operator fun invoke().
class GetAppsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(): List<AppDomainModel> = repository.getApps()
}
