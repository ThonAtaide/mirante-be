package br.com.mirantebackend.model.dto.authentication

import br.com.mirantebackend.annotations.NoArgsConstructor

@NoArgsConstructor
data class CreateUserDto(

    val nickname: String,
    val username: String,
    val email: String,
    val password: String
)
