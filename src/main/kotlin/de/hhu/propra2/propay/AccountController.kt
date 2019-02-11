package de.hhu.propra2.propay

import de.hhu.propra2.propay.services.MoneyService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class AccountController {

    @Autowired
    lateinit var moneyService: MoneyService

    @CrossOrigin(origins = ["*"])
    @GetMapping("/account/{account}")
    @ApiOperation(value = "Zeigt Accountinformationen an", notes = "Gibt die Informationen zu einem Account zurück. Wenn der Account noch nicht existierte, wird ein leerer Account angelegt.")
    fun getAccount(@PathVariable account: String): Account =
            moneyService.getAccount(account)

    @CrossOrigin(origins = ["*"])
    @PostMapping("/account/{account}")
    @ApiOperation(value = "Erhöhe / Verringere das Guthaben eines Accounts", notes = "Veränderung des Accountguthabens. Ein Account kann kein negatives Guthaben haben.")
    fun postDeposit(@PathVariable account: String,
                    @RequestParam amount: Double): Account =
            moneyService.deposit(account, amount)

    @CrossOrigin(origins = ["*"])
    @PostMapping("/account/{sourceAccount}/transfer/{targetAccount}")
    @ApiOperation(value = "Überweisung tätigen", notes = "Überweise Betrag vom `sourceAccount` auf auf `targetAccount`.")
    fun postTransfer(@PathVariable sourceAccount: String,
                     @PathVariable targetAccount: String,
                     @RequestParam amount: Double) =
            moneyService.transfer(sourceAccount, targetAccount, amount)
}
