package de.hhu.propra2.propay

import de.hhu.propra2.propay.services.MoneyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class AccountController {

    @Autowired
    lateinit var moneyService: MoneyService

    @GetMapping("/account/{account}")
    fun getAccount(@PathVariable account: String): Account =
            moneyService.getAccount(account)

    @PostMapping("/account/{account}")
    fun postDeposit(@PathVariable account: String,
                    @RequestParam amount: Double): Account =
            moneyService.deposit(account, amount)

    @PostMapping("/account/{sourceAccount}/transfer/{targetAccount}")
    fun postTransfer(@PathVariable sourceAccount: String,
                     @PathVariable targetAccount: String,
                     @RequestParam amount: Double) =
            moneyService.transfer(sourceAccount, targetAccount, amount)
}
