package ru.grigorev.vkeducationprj.domain.usecase

import ru.grigorev.vkeducationprj.domain.repository.AppRepository
import javax.inject.Inject

// Юзкейс: загрузить рейтинги ПАРАЛЛЕЛЬНО (ДЗ 9, задание 2).
class LoadRatingsInParallelUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(ids: List<String>): Map<String, Float> =
        repository.loadRatingsInParallel(ids)
}
