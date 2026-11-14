package pl.michallysak.application.offer.web

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import pl.michallysak.domain.model.OfferStatus
import pl.michallysak.domain.repository.OfferRepository
import pl.michallysak.domain.valueobject.OfferId
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNotEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OfferControllerIT @Autowired constructor(
    private val client: TestRestTemplate,
    private val offers: OfferRepository,
) {
    @Test
    fun `creates and publishes a new offer without a preexisting draft`() {
        // Given
        val priceAmount = BigDecimal("25.00")
        val request = mapOf("title" to "Book", "priceAmount" to priceAmount)

        // When
        val response = publish(request)

        // Then
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val body = assertNotNull(response.body)
        val expected = OfferResponseDto(
            id = body.id,
            title = "Book",
            priceAmount = priceAmount,
            priceCurrency = "PLN",
            status = OfferStatus.ACTIVE,
        )
        assertEquals(expected, body)
        val offerId = OfferId.of(body.id)
        val stored = offers.findById(offerId)
        val saved = assertNotNull(stored)
        assertEquals("Book", saved.title)
        assertEquals(priceAmount, saved.price.amount)
        assertEquals("PLN", saved.price.currency.currencyCode)
        assertEquals(OfferStatus.ACTIVE, saved.status)
    }

    @Test
    fun `accepts a specified currency and zero price`() {
        // Given
        val request = mapOf("title" to "Book", "priceAmount" to 0, "priceCurrency" to "EUR")

        // When
        val response = publish(request)

        // Then
        assertEquals(HttpStatus.CREATED, response.statusCode)
        val body = assertNotNull(response.body)
        assertEquals("EUR", body.priceCurrency)
        assertEquals(0, body.priceAmount.signum())
        val offerId = OfferId.of(body.id)
        val stored = offers.findById(offerId)
        val saved = assertNotNull(stored)
        assertEquals("EUR", saved.price.currency.currencyCode)
    }

    @Test
    fun `each publish request creates a distinct offer`() {
        // Given
        val request = mapOf("title" to "Book", "priceAmount" to 25)

        // When
        val first = publish(request)
        val second = publish(request)

        // Then
        assertEquals(HttpStatus.CREATED, first.statusCode)
        assertEquals(HttpStatus.CREATED, second.statusCode)
        val firstId = assertNotNull(first.body).id
        val secondId = assertNotNull(second.body).id
        assertNotEquals(firstId, secondId)
        for (id in listOf(firstId, secondId)) {
            val offerId = OfferId.of(id)
            val stored = offers.findById(offerId)
            val saved = assertNotNull(stored)
            assertEquals(OfferStatus.ACTIVE, saved.status)
        }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            """{"title":" ","priceAmount":25}""",
            """{"title":"Book","priceAmount":-1}""",
            """{"priceAmount":25}""",
            """{"title":"Book"}""",
            """{"title":"Book","priceAmount":25,"priceCurrency":"INVALID"}""",
        ]
    )
    fun `invalid publication data returns bad request`(json: String) {
        // Given
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val request = HttpEntity(json, headers)

        // When
        val response = client.postForEntity("/offers", request, String::class.java)

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    private fun publish(request: Map<String, Any>) =
        client.postForEntity("/offers", request, OfferResponseDto::class.java)
}
