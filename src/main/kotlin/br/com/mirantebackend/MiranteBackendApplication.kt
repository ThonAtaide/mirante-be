package br.com.mirantebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@EnableMongoAuditing
@SpringBootApplication
class MiranteBackendApplication

fun main(args: Array<String>) {
    runApplication<MiranteBackendApplication>(*args)
}
