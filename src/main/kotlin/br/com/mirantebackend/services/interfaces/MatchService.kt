package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.PageDto
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
        cup: String? = null,
        field: String? = null,
        playedAtStart: LocalDateTime? = null,
        playedAtEnd: LocalDateTime? = null,
        matchEnded: Boolean? = null,
        pageNumber: Int,
        pageSize: Int
    ): PageDto<MatchDto>

}