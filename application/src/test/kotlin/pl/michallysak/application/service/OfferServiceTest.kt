package pl.michallysak.application.service

import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import pl.michallysak.domain.model.OfferStatus
import pl.michallysak.domain.repository.OfferRepository
import pl.michallysak.domain.valueobject.Money
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class OfferServiceTest {
    @Test
    fun `creates publishes and saves a new offer`() {
        // Given
        val offers = mock(OfferRepository::class.java)
        val service = OfferService(offers)
        val title = "Book"
        val price = Money.of(BigDecimal("25.00"))

        // When
        val offer = service.publish(title, price)

        // Then
        assertEquals(title, offer.title)
        assertEquals(price, offer.price)
        assertEquals(OfferStatus.ACTIVE, offer.status)
        verify(offers).save(offer)
    }

}
