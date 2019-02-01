package de.hhu.propra2.propay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PropayApplication

fun main(args: Array<String>) {
	runApplication<PropayApplication>(*args)
}

