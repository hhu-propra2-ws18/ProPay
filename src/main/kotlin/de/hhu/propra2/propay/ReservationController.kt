package de.hhu.propra2.propay

import de.hhu.propra2.propay.services.ReservationService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController {

    @Autowired
    lateinit var reservationService: ReservationService

    @PostMapping("/reservation/reserve/{account}/{targetAccount}")
    @ApiOperation(value = "Reservierungen erzeugen", notes = "Reserviert einen vorgegebenen Betrag auf dem `account`. Benötigt einen Zielaccount, zu dem der reservierte Betrag bei Bedarf hin übertragen wird.")
    fun postReserve(@PathVariable account: String,
                    @PathVariable targetAccount: String,
                    @RequestParam amount: Double): Reservation =
            reservationService.reserve(account, targetAccount, amount)

    @PostMapping("/reservation/release/{account}")
    @ApiOperation(value = "Reservierung aufheben", notes = "Löse eine Reservierung. In diesem Fall wird dem Account sein Guthaben wieder zur freien Verfügung gegeben und die Reservierung wird restlos gelöscht.")
    fun postRelease(@PathVariable account: String,
                    @RequestParam reservationId: Long): Account =
            reservationService.releaseReservation(account, reservationId)

    @PostMapping("/reservation/punish/{account}")
    @ApiOperation(value = "Reservierung aufheben und Account bestrafen", notes = "Zieht dem Account das reservierte Guthaben ab und überträgt den reservierten Betrag an den zuvor definierten `targetAccount`.")
    fun postPunish(@PathVariable account: String,
                   @RequestParam reservationId: Long): Account =
            reservationService.punishAndTransferReservation(account, reservationId)
}