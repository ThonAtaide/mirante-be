package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.model.dto.matches.MatchDto
import br.com.mirantebackend.model.dto.pageable.PageDto
import java.time.LocalDateTime

interface MatchService {

    fun createMatch(championshipId: String, matchDto: MatchDto): MatchDto

    fun updateMatch(championshipId: String, matchId: String, matchDto: MatchDto): MatchDto

    fun findById(championshipId: String, matchId: String): MatchDto

    fun findAll(
        championshipId: String? = null,
        championshipName: String? = null,
        principal: String? = null,
        challenger: String? = null,
        field: String? = null,
        playedAtAfter: LocalDateTime? = null,
        playedAtBefore: LocalDateTime? = null,
        matchEnded: Boolean? = null,
        pageNumber: Int,
        pageSize: Int
    ): PageDto<MatchDto>
}
