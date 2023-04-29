package br.com.mirantebackend.controller.vo.authentication

import br.com.mirantebackend.annotations.NoArgsConstructor
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@NoArgsConstructor
data class RecoverUserCredentialsVo(

    @field:NotNull(message = "É preciso informar o e-mail")
    @field:NotEmpty(message = "É preciso informar o e-mail")
    val email: String? = null,
)
