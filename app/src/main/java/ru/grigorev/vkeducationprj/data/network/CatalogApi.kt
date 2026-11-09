package ru.grigorev.vkeducationprj.data.network

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
}
