package pl.michallysak.domain.valueobject

import java.util.UUID

@JvmInline
value class OfferId(val value: UUID) {
    override fun toString(): String = value.toString()

    companion object {
        fun of(value: UUID): OfferId = OfferId(value)
    }
}
