package br.com.mirantebackend.dto.matches

import br.com.mirantebackend.dto.pageable.RecordDto
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class MatchDto(
    var id: String?,

    @NotNull(message = "É preciso informar o campo.")
    @NotEmpty(message = "É preciso informar o campo.")
    var field: String,

    @NotNull(message = "É preciso informar a data e horário do jogo.")
    @NotEmpty(message = "É preciso informar a data e horário do jogo.")
    var playedAt: LocalDateTime,

    @NotNull(message = "É preciso informar o time mandante.")
    var principal: TeamDto,

    @NotNull(message = "É preciso informar o time desafiante.")
    var challenger: TeamDto,

    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null,

    var matchEnded: Boolean = false,
) : RecordDto