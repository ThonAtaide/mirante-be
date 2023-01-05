package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.PageDto
import java.util.*

interface MatchDao {

    fun save(championshipId: String, matchDto: MatchDto): MatchDto

    fun update(championshipId: String, matchId: String, matchDto: MatchDto): MatchDto

    fun findById(championshipId: String, matchId: String): Optional<MatchDto>

    fun findAll(
        championshipId: String? = null,
        championshipName: String? = null,
        season: String? = null,
        organizedBy: String? = null,
        principal: String? = null,
        challenger: String? = null,
        field: String? = null,
        playedAt: String? = null,
        matchEnded: Boolean? = null,
        pageNumber: Int,
        pageSize: Int

    ): PageDto<MatchDto>

}