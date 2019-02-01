package de.hhu.propra2.propay

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class AccountController {

    @Autowired
    lateinit var moneyService: MoneyService

    @GetMapping("/{team}/{account}")
    fun getAccount(@PathVariable team: String,
                   @PathVariable account: String): Account =
            moneyService.getAccount(team, account)

    @PostMapping("/{team}/{account}")
    fun postDeposit(@PathVariable team: String,
                    @PathVariable account: String,
                    @RequestParam amount: Double): Account =
            moneyService.deposit(team, account, amount)

    @PostMapping("/{team}/{sourceAccount}/{targetAccount}")
    fun postTransfer(@PathVariable team: String,
                     @PathVariable sourceAccount: String,
                     @PathVariable targetAccount: String,
                     @RequestParam amount: Double) =
            moneyService.transfer(team, sourceAccount, targetAccount, amount)
}
