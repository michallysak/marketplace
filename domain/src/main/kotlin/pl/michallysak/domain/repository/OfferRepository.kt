package pl.michallysak.domain.repository

import pl.michallysak.domain.model.Offer
import pl.michallysak.domain.valueobject.OfferId

interface OfferRepository {
    fun findById(id: OfferId): Offer?
    fun save(offer: Offer)
}
