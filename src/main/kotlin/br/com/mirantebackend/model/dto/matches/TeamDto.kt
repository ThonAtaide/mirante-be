package br.com.mirantebackend.model.dto.matches

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class TeamDto(

    @field:NotNull(message = "É preciso informar o campo.")
    @field:NotEmpty(message = "É preciso informar o campo.")
    var name: String?,

    var score: Int? = null
)
