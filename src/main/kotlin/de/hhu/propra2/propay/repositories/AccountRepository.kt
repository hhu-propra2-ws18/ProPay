package de.hhu.propra2.propay.repositories

import de.hhu.propra2.propay.entities.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, Long> {
    fun findByAccount(account: String): Account?
}