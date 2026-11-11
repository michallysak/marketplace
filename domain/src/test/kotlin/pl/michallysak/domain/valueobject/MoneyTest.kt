package pl.michallysak.domain.valueobject

import java.math.BigDecimal
import java.util.Currency
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MoneyTest {
    @Test
    fun `uses PLN by default`() {
        // Given
        val amount = BigDecimal("12.50")

        // When
        val money = Money.of(amount)

        // Then
        assertEquals(Currency.getInstance("PLN"), money.currency)
    }

    @Test
    fun `rejects negative amounts`() {
        // Given
        val amount = BigDecimal("-0.01")

        // When
        val error = assertFailsWith<IllegalArgumentException> { Money.of(amount) }

        // Then
        assertEquals("Money amount cannot be negative", error.message)
    }

    @Test
    fun `accepts another currency`() {
        // Given
        val currency = Currency.getInstance("EUR")

        // When
        val money = Money.of(BigDecimal("1.00"), currency)

        // Then
        assertEquals(currency, money.currency)
    }
}
