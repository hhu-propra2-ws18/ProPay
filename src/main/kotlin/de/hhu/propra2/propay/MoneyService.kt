package de.hhu.propra2.propay

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MoneyService {
    @Autowired
    lateinit var accountRepository: AccountRepository

    fun getAccount(team: String, account: String): Account =
            accountRepository.findByTeamAndAccount(team, account)
                    ?: Account(null, team, account, 0.0)

    fun deposit(team: String, account: String, amount: Double): Account {
        val acc = getAccount(team, account)
        if (amount < 0) {
            throw AttemptedRobberyException(amount)
        }
        acc.amount += amount

        return accountRepository.save(acc)
    }

    fun transfer(team: String, sourceAccount: String, targetAccount: String, amount: Double) {
        val source = this.getAccount(team, sourceAccount)
        val target = this.getAccount(team, targetAccount)

        if (amount < 0) {
            throw AttemptedRobberyException(amount)
        }

        if (!source.canCover(amount)) {
            throw InsufficientFundsException(source, amount)
        }

        source.amount -= amount
        target.amount += amount

        accountRepository.save(source)
        accountRepository.save(target)
    }

}

