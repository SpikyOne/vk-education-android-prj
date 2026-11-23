package ru.grigorev.vkeducationprj.domain.usecase

import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

// Юзкейс: загрузить рейтинги ПОСЛЕДОВАТЕЛЬНО (ДЗ 9, задание 2).
class LoadRatingsSequentiallyUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(ids: List<String>): Map<String, Float> =
        repository.loadRatingsSequentially(ids)
}
