package br.com.mirantebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableMongoAuditing
@SpringBootApplication
class MiranteBackendApplication

fun main(args: Array<String>) {
    val encode = BCryptPasswordEncoder().encode("test1234")
    println("Senha: $encode")
    runApplication<MiranteBackendApplication>(*args)
}
