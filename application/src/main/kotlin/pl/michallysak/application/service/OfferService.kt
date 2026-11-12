package pl.michallysak.application.service

import org.springframework.stereotype.Service
import pl.michallysak.domain.model.Offer
import pl.michallysak.domain.repository.OfferRepository
import pl.michallysak.domain.valueobject.Money
import pl.michallysak.domain.valueobject.OfferId
import java.util.UUID

@Service
class OfferService(private val offers: OfferRepository) {
    fun publish(title: String, price: Money): Offer {
        val id = OfferId.of(UUID.randomUUID())
        val offer = Offer.create(id, title, price)
        offer.publish()
        offers.save(offer)
        return offer
    }
}
