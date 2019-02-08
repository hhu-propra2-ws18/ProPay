package de.hhu.propra2.propay

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController {

    @Autowired
    lateinit var moneyService: MoneyService

    @PostMapping("/reservation/reserve/{account}/{targetAccount}")
    fun postReserve(@PathVariable account: String,
                    @PathVariable targetAccount: String,
                    @RequestParam amount: Double): Reservation =
            moneyService.reserve(account, targetAccount, amount)

    @PostMapping("/reservation/release/{account}")
    fun postRelease(@PathVariable account: String,
                    @RequestParam reservationId: Long): Account =
            moneyService.releaseReservation(account, reservationId)

    @PostMapping("/reservation/punish/{account}")
    fun postPunish(@PathVariable account: String,
                   @RequestParam reservationId: Long): Account =
            moneyService.punishAndTransferReservation(account, reservationId)
}