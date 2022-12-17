package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.services.dto.matches.MatchDto

interface MatchService {

    fun createMatch(matchDto: MatchDto): MatchDto

    fun updateMatch(matchId: String, matchDto: MatchDto): MatchDto

    fun findById(matchId: String): MatchDto

    fun findAll(): List<MatchDto>

}