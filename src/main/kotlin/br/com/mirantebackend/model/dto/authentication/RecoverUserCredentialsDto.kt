package br.com.mirantebackend.model.dto.authentication

import br.com.mirantebackend.annotations.NoArgsConstructor

@NoArgsConstructor
data class RecoverUserCredentialsDto(
    val email: String,
)
