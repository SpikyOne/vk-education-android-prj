package ru.grigorev.vkeducationprj.data.network

import kotlinx.coroutines.delay
import retrofit2.http.GET
import retrofit2.http.Path
import ru.grigorev.vkeducationprj.data.model.AppDetailsDto
import ru.grigorev.vkeducationprj.data.model.AppDto

interface CatalogApi {

    // GET http://185.103.109.134/catalog
    @GET("catalog")
    suspend fun getCatalog(): List<AppDto>

    // В тексте задания адрес указан как http://185.103.109.134/{id},
    // но фактически сервер отдаёт подробности приложения по пути /catalog/{id}.
    // GET http://185.103.109.134/catalog/{id}
    @GET("catalog/{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDetailsDto

    // ДЗ 9: рейтинг приложения по id.
    // Реального эндпоинта на сервере пока нет, поэтому сетевой запрос эмулируется
    // задержкой delay(1500) и случайным значением 3.0..5.0 — ровно как в тексте задания.
    // Kotlin компилирует тело метода интерфейса в default-метод JVM,
    // Retrofit проксирует только аннотированные методы, поэтому вызов дойдёт до этого тела.
    suspend fun getRating(id: String): Float {
        delay(1500) // эмуляция сетевого запроса
        return (30..50).random() / 10f
    }
}
