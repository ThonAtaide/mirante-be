package br.com.mirantebackend.dto.championship

import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.RecordDto
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ChampionshipDto(
    //TODO Testar remover a possibilidade de campos nulos em campos que não devem ser nulos
    val id: String? = null,

    @NotNull(message = "É preciso informar o nome da competição.")
    @NotEmpty(message = "É preciso informar o nome da competição.")
    var name: String?,

    var organizedBy: String? = null,

    @NotNull(message = "É preciso informar a temporada.")
    @NotEmpty(message = "É preciso informar a temporada.")
    var season: String? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var matches: MutableList<MatchDto>? = null,

    val createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null

) : RecordDto