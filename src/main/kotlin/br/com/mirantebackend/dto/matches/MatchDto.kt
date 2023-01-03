package br.com.mirantebackend.dto.matches

import br.com.mirantebackend.dto.pageable.RecordDto
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class MatchDto(
    var id: String?,

    @field:NotNull(message = "É preciso informar o campo.")
    @field:NotEmpty(message = "É preciso informar o campo.")
    var field: String? = null,

    @field:NotNull(message = "É preciso informar a data e horário do jogo.")
    var playedAt: LocalDateTime? = null,

    @field:NotNull(message = "É preciso informar o time mandante.")
    var principal: TeamDto? = null,

    @field:NotNull(message = "É preciso informar o time desafiante.")
    var challenger: TeamDto? = null,

    var matchEnded: Boolean = false,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var championship: ChampionshipDtoReduced? = null,

    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null

) : RecordDto {
    data class ChampionshipDtoReduced(
        var id: String? = null,
        var name: String? = null,
    )
}