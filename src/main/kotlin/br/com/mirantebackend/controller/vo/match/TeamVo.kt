package br.com.mirantebackend.controller.vo.match

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class TeamVo(

    @field:NotNull(message = "É preciso informar o campo.")
    @field:NotEmpty(message = "É preciso informar o campo.")
    var name: String?,

    var score: Int? = null
)
