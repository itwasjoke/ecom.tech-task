package com.ecomtask.itwas.joke

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JokeApplication

fun main(args: Array<String>) {
	runApplication<JokeApplication>(*args)
}
