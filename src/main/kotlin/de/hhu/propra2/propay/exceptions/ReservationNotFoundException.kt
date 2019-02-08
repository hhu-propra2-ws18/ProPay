package de.hhu.propra2.propay.exceptions

import de.hhu.propra2.propay.Account
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ReservationNotFoundException(val acc: Account, reservationId: Long)
    : RuntimeException("No reservation with id ${reservationId} found on "
        + "account ${acc.account}") {

}
