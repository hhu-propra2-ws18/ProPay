package de.hhu.propra2.propay

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PropayApplication

fun main(args: Array<String>) {
    SpringApplication.run(PropayApplication::class.java, *args)
}

