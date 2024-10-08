package com.ecomtask.itwas.joke

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class JokeApplication

fun main(args: Array<String>) {
	runApplication<JokeApplication>(*args)
}
