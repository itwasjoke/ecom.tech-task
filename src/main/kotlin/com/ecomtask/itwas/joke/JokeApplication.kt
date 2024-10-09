package com.ecomtask.itwas.joke

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
open class JokeApplication

fun main(args: Array<String>) {
	runApplication<JokeApplication>(*args)
}
