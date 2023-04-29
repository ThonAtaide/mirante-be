package br.com.mirantebackend.controller.vo.authentication

import br.com.mirantebackend.annotations.NoArgsConstructor
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@NoArgsConstructor
data class CreateUserVo(

    @field:NotNull(message = "É preciso informar um username")
    @field:NotEmpty(message = "É preciso informar um username")
    val username: String? = null,

    @field:NotNull(message = "É preciso informar um apelido")
    @field:NotEmpty(message = "É preciso informar um apelido")
    val nickname: String? = null,

    @field:NotNull(message = "É preciso informar um e-mail")
    @field:NotEmpty(message = "É preciso informar um e-mail")
    val email: String? = null,

    @field:NotNull(message = "É preciso informar uma senha válida")
    @field:NotEmpty(message = "É preciso informar uma senha válida.")
    val password: String? = null
)
