package de.hhu.propra2.propay

import de.hhu.propra2.propay.exceptions.AttemptedRobberyException
import de.hhu.propra2.propay.exceptions.InsufficientFundsException
import de.hhu.propra2.propay.exceptions.ReservationNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MoneyService {
    @Autowired
    lateinit var accountRepository: AccountRepository
    @Autowired
    lateinit var reservationRepository: ReservationRepository

    fun getAccount(account: String): Account =
            accountRepository.findByAccount(account)
                    ?: Account(null, account, 0.0)

    fun deposit(account: String, amount: Double): Account {
        val acc = getAccount(account)
        if (amount < 0) {
            throw AttemptedRobberyException(amount)
        }
        acc.amount += amount

        return accountRepository.save(acc)
    }

    fun transfer(sourceAccount: String, targetAccount: String, amount: Double) {
        val source = this.getAccount(sourceAccount)
        val target = this.getAccount(targetAccount)

        transfer(source, target, amount)
    }

    private fun transfer(source: Account, target: Account, amount: Double): Account {
        if (amount < 0) {
            throw AttemptedRobberyException(amount)
        }

        if (!source.canCover(amount)) {
            throw InsufficientFundsException(source, amount)
        }

        source.amount -= amount
        target.amount += amount

        accountRepository.save(source)
        return accountRepository.save(target)
    }

    fun reserve(account: String, targetAccount: String, amount: Double): Reservation {
        val acc = this.getAccount(account)
        val target = this.getAccount(targetAccount)
        if (amount < 0) {
            throw AttemptedRobberyException(amount)
        }

        if (!acc.canCover(amount)) {
            throw InsufficientFundsException(acc, amount)
        }

        val reservation = reservationRepository.save(Reservation(null, amount, target))
        acc.reservations.add(reservation)
        accountRepository.save(acc)
        return reservation
    }

    fun releaseReservation(account: String, reservationId: Long): Account {
        val acc = this.getAccount(account)
        val reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow { ReservationNotFoundException(acc, reservationId) }
        acc.reservations.remove(reservation)
        return accountRepository.save(acc)
    }

    fun punishAndTransferReservation(account: String, reservationId: Long): Account {
        val acc = this.getAccount(account)
        val reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow { ReservationNotFoundException(acc, reservationId) }

        val target = reservation.targetAccount

        acc.reservations.remove(reservation)
        return this.transfer(acc, target, reservation.amount)
    }

}
