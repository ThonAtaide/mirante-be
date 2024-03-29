package br.com.mirantebackend.controller.vo.championship

import br.com.mirantebackend.model.dto.pageable.RecordDto
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ChampionshipVo(

    var id: String? = null,

    @field:NotNull(message = "É preciso informar o nome da competição.")
    @field:NotEmpty(message = "É preciso informar o nome da competição.")
    var name: String? = null,

    var organizedBy: String? = null,

    @field:NotNull(message = "É preciso informar a temporada.")
    @field:NotEmpty(message = "É preciso informar a temporada.")
    var season: String? = null,

    val createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null

) : RecordDto
