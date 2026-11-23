package ru.grigorev.vkeducationprj.data.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import ru.grigorev.vkeducationprj.fakes.TestData

// ДЗ 10: тесты маппера DTO <-> Domain.
class AppMapperTest {

    private val mapper = AppMapper()

    @Test
    fun `map переносит все поля dto в домен-модель`() {
        val result = mapper.map(TestData.dto1)

        assertEquals(TestData.domain1, result)
    }

    @Test
    fun `mapToDto переносит все поля домена обратно в dto`() {
        val result = mapper.mapToDto(TestData.domain1)

        assertEquals(TestData.dto1, result)
    }

    @Test
    fun `roundtrip dto - domain - dto сохраняет равенство`() {
        val roundtrip = mapper.mapToDto(mapper.map(TestData.dto1))

        assertEquals(TestData.dto1, roundtrip)
    }

    @Test
    fun `mapDetails переносит все поля включая список скриншотов`() {
        val result = mapper.mapDetails(TestData.detailsDto)

        assertEquals(TestData.detailsDomain, result)
    }

    @Test
    fun `mapDetailsToDto переносит все поля деталей обратно`() {
        val result = mapper.mapDetailsToDto(TestData.detailsDomain)

        assertEquals(TestData.detailsDto, result)
    }

    @Test
    fun `roundtrip details dto - domain - dto сохраняет равенство`() {
        val roundtrip = mapper.mapDetailsToDto(mapper.mapDetails(TestData.detailsDto))

        assertEquals(TestData.detailsDto, roundtrip)
    }

    @Test
    fun `null поля сохраняются при преобразованиях`() {
        val dto = AppDto(
            id = "id-null",
            name = "Name",
            category = "Category",
            description = "Description",
            iconUrl = null
        )

        val domain = mapper.map(dto)

        assertNull(domain.iconUrl)
        assertEquals(dto, mapper.mapToDto(domain))
    }
}
