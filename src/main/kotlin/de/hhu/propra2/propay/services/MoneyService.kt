package de.hhu.propra2.propay.services

import de.hhu.propra2.propay.entities.Account
import de.hhu.propra2.propay.exceptions.AttemptedRobberyException
import de.hhu.propra2.propay.exceptions.InsufficientFundsException
import de.hhu.propra2.propay.exceptions.NiceTryException
import de.hhu.propra2.propay.repositories.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MoneyService(private @Autowired val accountRepository: AccountRepository) {

    fun getAccount(account: String): Account =
            accountRepository.findByAccount(account)
                    ?: Account(null, account, 0.0)

    fun deposit(accountName: String, amount: Double): Account {
        val acc = getAccount(accountName)
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

    fun transfer(source: Account, target: Account, amount: Double): Account {
        if (target == source) {
            throw NiceTryException(source)
        }

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

    fun save(acc: Account) = accountRepository.save(acc)
}
