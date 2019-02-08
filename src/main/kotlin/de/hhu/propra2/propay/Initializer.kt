package de.hhu.propra2.propay

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.stereotype.Component
import javax.servlet.ServletContext

@Component
class Initializer : ServletContextInitializer {
    @Autowired
    lateinit var accountRepository: AccountRepository

    override fun onStartup(ctx: ServletContext?) {
        val account = Account(null, "foo", 100.0)
        accountRepository.save(account)

        val account2 = Account(null, "bar", 200.0)
        accountRepository.save(account2)

        val account3 = Account(null, "ipsum", -100.0)
        accountRepository.save(account3)

    }


}
