package pl.michallysak.application.offer.web

import pl.michallysak.domain.model.Offer
import pl.michallysak.domain.model.OfferStatus
import java.math.BigDecimal
import java.util.UUID

data class OfferResponseDto(
    val id: UUID,
    val title: String,
    val priceAmount: BigDecimal,
    val priceCurrency: String,
    val status: OfferStatus,
) {
    companion object {
        fun from(offer: Offer) = OfferResponseDto(
            id = offer.id.value,
            title = offer.title,
            priceAmount = offer.price.amount,
            priceCurrency = offer.price.currency.currencyCode,
            status = offer.status,
        )
    }
}
