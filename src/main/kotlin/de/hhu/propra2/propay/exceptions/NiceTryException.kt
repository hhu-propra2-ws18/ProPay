package de.hhu.propra2.propay.exceptions

import de.hhu.propra2.propay.Account
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class NiceTryException(source: Account) : RuntimeException("Source and target "
        + "accounts must be different ${source.account}")
