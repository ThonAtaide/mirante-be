package br.com.mirantebackend.dto.championship

import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.RecordDto
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ChampionshipDto(

    val id: String? = null,

    @NotNull(message = "É preciso informar o nome da competição.")
    @NotEmpty(message = "É preciso informar o nome da competição.")
    var name: String?,

    var organizedBy: String? = null,

    @NotNull(message = "É preciso informar a temporada.")
    @NotEmpty(message = "É preciso informar a temporada.")
    var season: String? = null,

    var matches: MutableList<MatchDto> = mutableListOf(),

    val createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null

) : RecordDto