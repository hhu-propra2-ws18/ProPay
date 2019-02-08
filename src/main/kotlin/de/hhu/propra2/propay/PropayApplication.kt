package de.hhu.propra2.propay

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.swing.Spring

@SpringBootApplication
open class PropayApplication

fun main(args: Array<String>) {
    SpringApplication.run(PropayApplication::class.java, *args)
}

