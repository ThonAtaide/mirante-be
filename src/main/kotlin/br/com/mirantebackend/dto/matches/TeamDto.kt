package br.com.mirantebackend.dto.matches

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class TeamDto(

    @NotNull(message = "É preciso informar o campo.")
    @NotEmpty(message = "É preciso informar o campo.")
    var name: String,

    var score: Int? = null
)