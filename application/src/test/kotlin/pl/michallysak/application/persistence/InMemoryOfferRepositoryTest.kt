package pl.michallysak.application.persistence

import pl.michallysak.domain.model.Offer
import pl.michallysak.domain.model.OfferStatus
import pl.michallysak.domain.valueobject.Money
import pl.michallysak.domain.valueobject.OfferId
import java.math.BigDecimal
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class InMemoryOfferRepositoryTest {
    @Test
    fun `finds saved offers by their respective ids`() {
        // Given
        val repository = InMemoryOfferRepository()
        val book = createOffer("Book")
        val game = createOffer("Game")

        // When
        repository.save(book)
        repository.save(game)

        // Then
        for (expected in listOf(book, game)) {
            val saved = assertNotNull(repository.findById(expected.id))
            assertEquals(expected.id, saved.id)
            assertEquals(expected.title, saved.title)
            assertEquals(expected.price, saved.price)
            assertEquals(expected.status, saved.status)
        }
    }

    @Test
    fun `returns null for an unknown id`() {
        // Given
        val repository = InMemoryOfferRepository()
        repository.save(createOffer("Book"))
        val missingId = OfferId.of(UUID.randomUUID())

        // When
        val offer = repository.findById(missingId)

        // Then
        assertNull(offer)
    }

    @Test
    fun `replaces an offer saved with the same id`() {
        // Given
        val repository = InMemoryOfferRepository()
        val original = createOffer("Book")
        repository.save(original)
        val updated = Offer.create(original.id, "Updated book", Money.of(BigDecimal("30.00")))
        updated.publish()

        // When
        repository.save(updated)

        // Then
        val saved = assertNotNull(repository.findById(original.id))
        assertEquals(original.id, saved.id)
        assertEquals("Updated book", saved.title)
        assertEquals(Money.of(BigDecimal("30.00")), saved.price)
        assertEquals(OfferStatus.ACTIVE, saved.status)
    }

    private fun createOffer(title: String) = Offer.create(
        OfferId.of(UUID.randomUUID()),
        title,
        Money.of(BigDecimal("25.00")),
    )
}
