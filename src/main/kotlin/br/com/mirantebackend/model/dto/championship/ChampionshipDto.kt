package br.com.mirantebackend.model.dto.championship

import br.com.mirantebackend.model.dto.matches.MatchDto
import br.com.mirantebackend.model.dto.pageable.RecordDto
import java.time.LocalDateTime

data class ChampionshipDto(

    var id: String? = null,

    var name: String,

    var organizedBy: String,

    var season: String,

    var matches: MutableList<MatchDto>? = null,

    val createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null

) : RecordDto