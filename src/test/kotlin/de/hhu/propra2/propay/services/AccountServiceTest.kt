package de.hhu.propra2.propay.services

import de.hhu.propra2.propay.entities.Account
import de.hhu.propra2.propay.exceptions.AttemptedRobberyException
import de.hhu.propra2.propay.exceptions.InsufficientFundsException
import de.hhu.propra2.propay.exceptions.NiceTryException
import de.hhu.propra2.propay.repositories.AccountRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


class AccountServiceTest {

    private lateinit var accountRepository: AccountRepository
    private lateinit var accountService: AccountService

    @Before
    fun setup() {

        accountRepository = mock(AccountRepository::class.java)
        accountService = AccountService(accountRepository)
    }

    @Test
    fun getAccount() {
        Mockito.`when`(accountRepository.findByAccount("asdf"))
                .thenReturn(null)
        Mockito.`when`(accountRepository.findByAccount("foo"))
                .thenReturn(Account(123, "foo", 0.0))

        assertNull(accountService.getAccount("asdf").id)

        assertEquals("asdf",
                accountService.getAccount("asdf").account)

        assertEquals(123L, accountService.getAccount("foo").id)

        assertEquals("foo",
                accountService.getAccount("foo").account)


    }

    @Test
    fun deposit() {
        val amount = 100.0

        Mockito.`when`(accountRepository.findByAccount("foo"))
                .thenReturn(Account(123, "foo", 0.0))
        Mockito
                .`when`(accountRepository.save(Account(123, "foo", amount)))
                .thenReturn(Account(123, "foo", amount))

        assertEquals(amount, accountService.deposit("foo", amount).amount, 0.1)
    }

    @Test(expected = AttemptedRobberyException::class)
    fun depositNegativeAmount() {
        Mockito.`when`(accountRepository.findByAccount("foo"))
                .thenReturn(Account(123, "foo", 0.0))

        accountService.deposit("foo", -100.0)
    }


    @Test(expected = AttemptedRobberyException::class)
    fun transferNegativeAmount() {
        val acc1 = Account(1, "User 1", .0)
        val acc2 = Account(2, "User 2", .0)
        accountService.transfer(acc1, acc2, -1.0)
    }

    @Test(expected = InsufficientFundsException::class)
    fun transferWithInsufficientFunds() {
        val acc1 = Account(1, "User 1", .0)
        val acc2 = Account(2, "User 2", .0)

        accountService.transfer(acc1, acc2, 1.1)
    }

    @Test
    fun transferBetweenAcounts() {
        val acc1 = Account(1, "User 1", 10.0)
        val acc2 = Account(2, "User 2", 10.0)

        Mockito.`when`(accountRepository.save(acc1)).thenReturn(acc1)
        Mockito.`when`(accountRepository.save(acc2)).thenReturn(acc2)

        accountService.transfer(acc1, acc2, 1.1)

        assertEquals(8.9, acc1.amount, .1)
        assertEquals(11.1, acc2.amount, .1)
    }

    @Test(expected = NiceTryException::class)
    fun transferBetweenSameAcount() {
        val acc1 = Account(1, "User 1", 10.0)

        Mockito.`when`(accountRepository.save(acc1)).thenReturn(acc1)

        accountService.transfer(acc1, acc1, 1.1)
    }
}