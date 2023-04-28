package br.com.mirantebackend.model.dto.authentication

import br.com.mirantebackend.annotations.NoArgsConstructor
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@NoArgsConstructor
data class RecoverUserCredentialsDto(
    val email: String,
)