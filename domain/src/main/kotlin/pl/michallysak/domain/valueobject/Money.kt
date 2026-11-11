package pl.michallysak.domain.valueobject

import java.math.BigDecimal
import java.util.Currency

data class Money(
    val amount: BigDecimal,
    val currency: Currency = Currency.getInstance("PLN"),
) {
    init {
        require(amount >= BigDecimal.ZERO) { "Money amount cannot be negative" }
    }

    companion object {
        fun of(amount: BigDecimal, currency: Currency = Currency.getInstance("PLN")) = Money(amount, currency)
    }
}
