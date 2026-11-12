package pl.michallysak.application.web

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal
import java.util.Currency

data class OfferPublishDto(
    @field:NotBlank
    val title: String,
    @field:DecimalMin("0")
    val priceAmount: BigDecimal,
    val priceCurrency: Currency = Currency.getInstance("PLN"),
)
