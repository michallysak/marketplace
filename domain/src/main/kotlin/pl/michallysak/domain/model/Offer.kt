package pl.michallysak.domain.model

import pl.michallysak.domain.valueobject.Money
import pl.michallysak.domain.valueobject.OfferId

class Offer private constructor(
    val id: OfferId,
    val title: String,
    val price: Money,
) {
    var status: OfferStatus = OfferStatus.DRAFT
        private set

    fun publish() {
        check(status == OfferStatus.DRAFT) { "Only draft offers can be published" }
        status = OfferStatus.ACTIVE
    }

    companion object {
        fun create(
            id: OfferId,
            title: String,
            price: Money,
        ): Offer {
            require(title.isNotBlank()) { "Offer title cannot be blank" }
            return Offer(id, title, price)
        }
    }
}
