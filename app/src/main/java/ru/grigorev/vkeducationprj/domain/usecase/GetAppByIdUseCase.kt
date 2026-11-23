package ru.grigorev.vkeducationprj.domain.usecase

import ru.grigorev.vkeducationprj.domain.model.AppDomainModel
import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

// Юзкейс: найти приложение в каталоге по id (или null, если его нет).
class GetAppByIdUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(id: String): AppDomainModel? = repository.getAppById(id)
}
