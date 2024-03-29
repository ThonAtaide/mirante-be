package br.com.mirantebackend.model.dto.championship

import br.com.mirantebackend.model.dto.pageable.RecordDto
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ChampionshipDto(

    var id: String? = null,

    @field:NotNull(message = "É preciso informar o nome da competição.")
    @field:NotEmpty(message = "É preciso informar o nome da competição")
    var name: String,

    @field:NotNull(message = "É preciso informar o nome da organização.")
    @field:NotEmpty(message = "É preciso informar o nome da organização")
    var organizedBy: String,

    @field:NotNull(message = "É preciso informar a temporada.")
    @field:NotEmpty(message = "É preciso informar a temporada.")
    var season: String,

    val createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null

) : RecordDto
