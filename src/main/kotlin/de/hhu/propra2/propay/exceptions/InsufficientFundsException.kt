package de.hhu.propra2.propay.exceptions

import de.hhu.propra2.propay.entities.Account
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.PAYMENT_REQUIRED)
class InsufficientFundsException(account: Account, requestedAmount: Double)
    : RuntimeException("Source account does not have sufficient funds "
        + "(currently ${account.availableAmount()}) for requested amount of ${requestedAmount}")
