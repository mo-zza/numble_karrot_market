package com.mozza.karrotmarket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class KarrotmarketApplication

fun main(args: Array<String>) {
    runApplication<KarrotmarketApplication>(*args)
}
