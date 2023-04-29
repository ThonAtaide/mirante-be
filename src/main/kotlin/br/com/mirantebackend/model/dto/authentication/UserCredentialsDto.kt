package br.com.mirantebackend.model.dto.authentication

import br.com.mirantebackend.annotations.NoArgsConstructor

@NoArgsConstructor
data class UserCredentialsDto(

    val username: String,
    val password: String
)
