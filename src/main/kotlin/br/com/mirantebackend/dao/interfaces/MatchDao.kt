package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.dto.matches.MatchDto
import java.util.*

interface MatchDao {

    fun save(championshipId: String, matchDto: MatchDto): MatchDto

    fun update(championshipId: String, matchId: String, matchDto: MatchDto): MatchDto

    fun findById(championshipId: String, matchId: String): Optional<MatchDto>

    fun findAllMatchesFromChampionship(
        championshipId: String? = null,
        championshipName: String? = null,
        season: String? = null,
        organizedBy: String? = null,
        principal: String? = null,
        challenger: String? = null,
        field: String? = null,
        played_at: String? = null,
        match_ended: Boolean? = null,
        pageNumber: Int,
        pageSize: Int

    ): List<MatchDto>

}