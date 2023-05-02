package br.com.mirantebackend.controller.vo.match

import com.fasterxml.jackson.annotation.JsonInclude
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class TeamVo(

    @field:NotNull(message = "É preciso informar o campo.")
    @field:NotEmpty(message = "É preciso informar o campo.")
    var name: String?,

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    var score: Int? = null
)
