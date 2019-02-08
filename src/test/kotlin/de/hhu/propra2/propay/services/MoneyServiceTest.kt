package de.hhu.propra2.propay.services

import de.hhu.propra2.propay.Account
import de.hhu.propra2.propay.AccountRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class MoneyServiceTest {

    @TestConfiguration
    class EmployeeServiceImplTestContextConfiguration {

        @Bean
        fun moneyService(): MoneyService = MoneyService()
    }

    @Autowired
    lateinit var moneyService: MoneyService

    @MockBean
    lateinit var accountRepository: AccountRepository

    @Test
    fun getAccount() {
        Mockito.`when`(accountRepository.findByAccount("asdf"))
                .thenReturn(null)
        Mockito.`when`(accountRepository.findByAccount("foo"))
                .thenReturn(Account(123, "foo", 0.0))

        assertNull(moneyService.getAccount("asdf").id)

        assertEquals("asdf",
                moneyService.getAccount("asdf").account)

        assertEquals(123L, moneyService.getAccount("foo").id)

        assertEquals("foo",
                moneyService.getAccount("foo").account)


    }

    @Test
    fun deposit() {
        val amount = 100.0

        Mockito.`when`(accountRepository.findByAccount("foo"))
                .thenReturn(Account(123, "foo", 0.0))
        Mockito
                .`when`(accountRepository.save(Account(123, "foo", amount)))
                .thenReturn(Account(123, "foo", amount))

        assertEquals(amount, moneyService.deposit("foo", amount).amount, 0.1)
    }

    @Test
    fun transfer() {
    }

    @Test
    fun transfer1() {
    }

    @Test
    fun save() {
    }
}