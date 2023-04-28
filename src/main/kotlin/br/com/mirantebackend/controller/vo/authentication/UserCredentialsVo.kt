package br.com.mirantebackend.controller.vo.authentication

import br.com.mirantebackend.annotations.NoArgsConstructor
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@NoArgsConstructor
data class UserCredentialsVo(

    @field:NotNull(message = "É preciso informar o username.")
    @field:NotEmpty(message = "É preciso informar o username.")
    val username: String? = null,

    @field:NotNull(message = "É preciso informar a senha.")
    @field:NotEmpty(message = "É preciso informar a senha.")
    val password: String? = null
)