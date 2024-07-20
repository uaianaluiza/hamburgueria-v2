package com.hamburguer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HamburguerApplication

fun main(args: Array<String>) {
	runApplication<HamburguerApplication>(*args)
}
