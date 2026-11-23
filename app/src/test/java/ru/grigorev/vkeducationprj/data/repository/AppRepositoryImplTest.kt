package ru.grigorev.vkeducationprj.data.repository

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import ru.grigorev.vkeducationprj.data.model.AppMapper
import ru.grigorev.vkeducationprj.fakes.FakeCatalogApi
import ru.grigorev.vkeducationprj.fakes.TestData

// ДЗ 10: тесты репозитория (дата-слой) на подменном CatalogApi.
class AppRepositoryImplTest {

    // --- getApps ---

    @Test
    fun `getApps маппит каталог из api в домен-модели`() = runTest {
        val api = FakeCatalogApi(catalog = TestData.catalog)
        val repository = AppRepositoryImpl(api, AppMapper())

        val result = repository.getApps()

        assertEquals(TestData.domainCatalog, result)
    }

    @Test
    fun `getApps кэширует каталог - повторный вызов не обращается к api`() = runTest {
        val api = FakeCatalogApi(catalog = TestData.catalog)
        val repository = AppRepositoryImpl(api, AppMapper())

        repository.getApps()
        repository.getApps()

        assertEquals(1, api.getCatalogCallCount)
    }

    // --- getAppById ---

    @Test
    fun `getAppById находит приложение с совпадающим id`() = runTest {
        val api = FakeCatalogApi(catalog = TestData.catalog)
        val repository = AppRepositoryImpl(api, AppMapper())

        val result = repository.getAppById(TestData.ID_1)

        assertEquals(TestData.domain1, result)
    }

    @Test
    fun `getAppById возвращает null если приложения нет`() = runTest {
        val api = FakeCatalogApi(catalog = TestData.catalog)
        val repository = AppRepositoryImpl(api, AppMapper())

        val result = repository.getAppById("no-such-id")

        assertNull(result)
    }

    // --- getAppDetails ---

    @Test
    fun `getAppDetails маппит детали приложения`() = runTest {
        val api = FakeCatalogApi(details = mapOf(TestData.ID_1 to TestData.detailsDto))
        val repository = AppRepositoryImpl(api, AppMapper())

        val result = repository.getAppDetails(TestData.ID_1)

        assertEquals(TestData.detailsDomain, result)
    }

    // --- рейтинги: значения ---

    @Test
    fun `loadRatingsSequentially возвращает рейтинг для каждого id`() = runTest {
        val api = FakeCatalogApi(ratingValue = 4.5f)
        val repository = AppRepositoryImpl(api, AppMapper())

        val result = repository.loadRatingsSequentially(TestData.ids10)

        assertEquals(TestData.ids10.associateWith { 4.5f }, result)
    }

    @Test
    fun `loadRatingsInParallel возвращает рейтинг для каждого id`() = runTest {
        val api = FakeCatalogApi(ratingValue = 3.7f)
        val repository = AppRepositoryImpl(api, AppMapper())

        val result = repository.loadRatingsInParallel(TestData.ids10)

        assertEquals(TestData.ids10.associateWith { 3.7f }, result)
    }

    // --- рейтинги: проверка сути ДЗ 9 через виртуальное время runTest ---

    @Test
    fun `последовательная загрузка занимает N умножить на delay`() = runTest {
        val api = FakeCatalogApi(ratingDelayMillis = 1000)
        val repository = AppRepositoryImpl(api, AppMapper())
        val ids = TestData.ids10.take(5)

        repository.loadRatingsSequentially(ids)

        // 5 запросов по 1000 мс виртуальной задержки друг за другом
        assertEquals(5000L, testScheduler.currentTime)
    }

    @Test
    fun `параллельная загрузка занимает время одного запроса`() = runTest {
        val api = FakeCatalogApi(ratingDelayMillis = 1000)
        val repository = AppRepositoryImpl(api, AppMapper())
        val ids = TestData.ids10.take(5)

        repository.loadRatingsInParallel(ids)

        // все 5 запросов идут одновременно: ждём только одну задержку
        assertEquals(1000L, testScheduler.currentTime)
    }
}
