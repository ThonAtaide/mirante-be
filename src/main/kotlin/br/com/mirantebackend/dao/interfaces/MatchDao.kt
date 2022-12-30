package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.dto.matches.MatchDto
import java.util.*

interface MatchDao {

    fun save(championshipId: String, matchDto: MatchDto): MatchDto

    fun update(championshipId: String, matchId: String, matchDto: MatchDto): MatchDto

    fun findById(championshipId: String, matchId: String): Optional<MatchDto>

    fun findAllMatchesFromChampionship(
        championshipName: String? = null,
        season: String? = null,
        organizedBy: String? = null,
        pageNumber: Int,
        pageSize: Int

    ): List<MatchDto>

    fun findAllMatches(
        championshipName: String? = null,
        season: String? = null,
        organizedBy: String? = null,
        pageNumber: Int,
        pageSize: Int
    ): List<MatchDto>
}