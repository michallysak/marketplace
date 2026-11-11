package pl.michallysak.domain.valueobject

import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class OfferIdTest {

    @Test
    fun `creates and formats UUID values`() {
        // Given
        val uuid = UUID.randomUUID()

        // When
        val offerId = OfferId.of(uuid)

        // Then
        assertEquals(uuid, offerId.value)
        assertEquals(uuid.toString(), offerId.toString())
    }
}
