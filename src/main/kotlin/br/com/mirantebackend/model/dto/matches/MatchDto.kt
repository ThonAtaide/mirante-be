package br.com.mirantebackend.model.dto.matches

import br.com.mirantebackend.model.dto.championship.ChampionshipDto
import br.com.mirantebackend.model.dto.pageable.RecordDto
import java.time.LocalDateTime

data class MatchDto(
    var id: String?,

    var field: String,

    var playedAt: LocalDateTime,

    var principal: TeamDto,

    var challenger: TeamDto,

    var matchEnded: Boolean = false,

    var championship: ChampionshipDto? = null,

    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null

) : RecordDto
