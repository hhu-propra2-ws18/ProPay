package de.hhu.propra2.propay

import org.springframework.data.repository.CrudRepository

interface AccountRepository : CrudRepository<Account, Long> {
    fun findByTeamAndAccount(group: String, account: String) : Account?
}