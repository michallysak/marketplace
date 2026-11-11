package pl.michallysak.domain.model

import pl.michallysak.domain.valueobject.Money
import pl.michallysak.domain.valueobject.OfferId
import java.math.BigDecimal
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class OfferTest {
    @Test
    fun `new offer has the supplied details and draft status`() {
        // Given
        val id = OfferId.of(UUID.randomUUID())
        val title = "Book"
        val price = Money.of(BigDecimal("25.00"))

        // When
        val offer = Offer.create(id, title, price)

        // Then
        assertEquals(id, offer.id)
        assertEquals(title, offer.title)
        assertEquals(price, offer.price)
        assertEquals(OfferStatus.DRAFT, offer.status)
    }

    @Test
    fun `draft can be published`() {
        // Given
        val offer = createOffer()

        // When
        offer.publish()

        // Then
        assertEquals(OfferStatus.ACTIVE, offer.status)
    }

    @Test
    fun `active offer cannot be published again`() {
        // Given
        val offer = createOffer()
        offer.publish()

        // When
        val error = assertFailsWith<IllegalStateException> { offer.publish() }

        // Then
        assertEquals("Only draft offers can be published", error.message)
        assertEquals(OfferStatus.ACTIVE, offer.status)
    }

    @Test
    fun `creation rejects a blank title`() {
        // Given
        val id = OfferId.of(UUID.randomUUID())
        val price = Money.of(BigDecimal.ONE)

        // When
        val error = assertFailsWith<IllegalArgumentException> { Offer.create(id, " ", price) }

        // Then
        assertEquals("Offer title cannot be blank", error.message)
    }

    private fun createOffer() = Offer.create(
        id = OfferId.of(UUID.randomUUID()),
        title = "Book",
        price = Money.of(BigDecimal("25.00")),
    )
}
