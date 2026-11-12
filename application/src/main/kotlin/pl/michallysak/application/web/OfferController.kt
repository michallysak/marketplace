package pl.michallysak.application.web

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.ResponseStatus
import pl.michallysak.domain.valueobject.Money
import pl.michallysak.application.service.OfferService

@RestController
@RequestMapping("/offers")
class OfferController(private val service: OfferService) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun publish(@Valid @RequestBody request: OfferPublishDto): OfferResponseDto {
        val price = Money.of(request.priceAmount, request.priceCurrency)
        val offer = service.publish(request.title, price)
        return OfferResponseDto.from(offer)
    }
}
