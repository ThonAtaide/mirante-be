package br.com.mirantebackend.model.dto.authentication

import br.com.mirantebackend.annotations.NoArgsConstructor

@NoArgsConstructor
data class UserSessionDto(
    val nickname: String,
    val jwtToken: String
)
