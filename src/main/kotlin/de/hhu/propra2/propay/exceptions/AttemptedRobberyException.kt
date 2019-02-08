package de.hhu.propra2.propay.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class AttemptedRobberyException(amount: Double)
    : RuntimeException("Amount must not be negative (received: ${amount}).")
