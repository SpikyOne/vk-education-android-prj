package ru.grigorev.vkeducationprj.domain.usecase

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import ru.grigorev.vkeducationprj.fakes.FakeAppRepository
import ru.grigorev.vkeducationprj.fakes.TestData

// ДЗ 10: тесты юзкейсов (доменный слой) на подменном репозитории.
class UseCasesTest {

    // --- GetAppsUseCase ---

    @Test
    fun `GetAppsUseCase возвращает каталог репозитория`() = runTest {
        val repository = FakeAppRepository(apps = TestData.domainCatalog)
        val useCase = GetAppsUseCase(repository)

        val result = useCase()

        assertEquals(TestData.domainCatalog, result)
    }

    @Test
    fun `GetAppsUseCase обращается к репозиторию ровно один раз`() = runTest {
        val repository = FakeAppRepository(apps = TestData.domainCatalog)
        val useCase = GetAppsUseCase(repository)

        useCase()

        assertEquals(1, repository.getAppsCallCount)
    }

    // --- GetAppByIdUseCase ---

    @Test
    fun `GetAppByIdUseCase находит приложение по id`() = runTest {
        val repository = FakeAppRepository(apps = TestData.domainCatalog)
        val useCase = GetAppByIdUseCase(repository)

        val result = useCase(TestData.ID_1)

        assertEquals(TestData.domain1, result)
    }

    @Test
    fun `GetAppByIdUseCase возвращает null если приложения нет`() = runTest {
        val repository = FakeAppRepository(apps = TestData.domainCatalog)
        val useCase = GetAppByIdUseCase(repository)

        val result = useCase("no-such-id")

        assertNull(result)
    }

    // --- LoadRatingsUseCase'ы ---

    @Test
    fun `LoadRatingsSequentiallyUseCase передаёт ids в репозиторий и возвращает результат`() = runTest {
        val ratings = mapOf(TestData.ID_1 to 4.5f, TestData.ID_2 to 3.5f)
        val repository = FakeAppRepository(ratings = ratings)
        val useCase = LoadRatingsSequentiallyUseCase(repository)

        val result = useCase(TestData.ids10)

        assertEquals(ratings, result)
        assertEquals(TestData.ids10, repository.lastSequentialIds)
    }

    @Test
    fun `LoadRatingsInParallelUseCase передаёт ids в репозиторий и возвращает результат`() = runTest {
        val ratings = mapOf(TestData.ID_1 to 4.2f, TestData.ID_2 to 3.9f)
        val repository = FakeAppRepository(ratings = ratings)
        val useCase = LoadRatingsInParallelUseCase(repository)

        val result = useCase(TestData.ids10)

        assertEquals(ratings, result)
        assertEquals(TestData.ids10, repository.lastParallelIds)
    }
}
