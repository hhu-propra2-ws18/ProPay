package de.hhu.propra2.propay

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.PAYMENT_REQUIRED)
class InsufficientFundsException(account: Account, requestedAmount: Double)
    : RuntimeException("Source account does not have sufficient funds " +
        "(currently ${account.amount}) for requested transfer of ${requestedAmount}")
