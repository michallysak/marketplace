package pl.michallysak.application.offer.persistence

import org.springframework.stereotype.Repository
import pl.michallysak.domain.model.Offer
import pl.michallysak.domain.repository.OfferRepository
import pl.michallysak.domain.valueobject.OfferId
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryOfferRepository : OfferRepository {
    private val offers = ConcurrentHashMap<OfferId, Offer>()

    override fun findById(id: OfferId): Offer? = offers[id]

    override fun save(offer: Offer) {
        offers[offer.id] = offer
    }
}
