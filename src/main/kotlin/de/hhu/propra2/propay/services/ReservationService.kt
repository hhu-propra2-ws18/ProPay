package de.hhu.propra2.propay.services

import de.hhu.propra2.propay.entities.Account
import de.hhu.propra2.propay.entities.Reservation
import de.hhu.propra2.propay.exceptions.AttemptedRobberyException
import de.hhu.propra2.propay.exceptions.InsufficientFundsException
import de.hhu.propra2.propay.exceptions.NiceTryException
import de.hhu.propra2.propay.exceptions.ReservationNotFoundException
import de.hhu.propra2.propay.repositories.ReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReservationService(private @Autowired val accountService: AccountService,
                         private @Autowired val reservationRepository: ReservationRepository) {


    fun reserve(account: String, targetAccount: String, amount: Double): Reservation {
        val acc = accountService.getAccount(account)
        val target = accountService.getAccount(targetAccount)

        if (target == acc) {
            throw NiceTryException(acc)
        }

        if (amount < 0) {
            throw AttemptedRobberyException(amount)
        }

        if (!acc.canCover(amount)) {
            throw InsufficientFundsException(acc, amount)
        }

        val reservation = reservationRepository.save(Reservation(null, amount, target))
        acc.reservations.add(reservation)
        accountService.save(acc)
        return reservation
    }

    fun releaseReservation(account: String, reservationId: Long): Account {
        val acc = accountService.getAccount(account)
        val reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow { ReservationNotFoundException(acc, reservationId) }
        acc.reservations.remove(reservation)
        return accountService.save(acc)
    }

    fun punishAndTransferReservation(account: String, reservationId: Long): Account {
        val acc = accountService.getAccount(account)
        val reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow { ReservationNotFoundException(acc, reservationId) }

        val target = reservation.targetAccount

        val result = accountService.transfer(acc, target, reservation.amount)
        acc.reservations.remove(reservation)
        return result
    }

}