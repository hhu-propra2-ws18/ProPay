package de.hhu.propra2.propay

import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, Long> {
    fun findByAccount(account: String): Account?
}