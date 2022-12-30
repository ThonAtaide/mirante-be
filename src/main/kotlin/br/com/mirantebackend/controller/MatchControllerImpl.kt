package br.com.mirantebackend.controller

import br.com.mirantebackend.controller.interfaces.MatchController
import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.PageDto
import br.com.mirantebackend.services.interfaces.MatchService
import org.springframework.web.bind.annotation.RestController

@RestController
class MatchControllerImpl(
    private val matchService: MatchService
) : MatchController {

    override fun registerMatch(
        championshipId: String,
        match: MatchDto
    ): MatchDto = matchService.createMatch(championshipId, match)


    override fun updateMatch(
        championshipId: String,
        matchId: String,
        match: MatchDto
    ): MatchDto = matchService.updateMatch(championshipId, matchId, match)


    override fun getMatch(championshipId: String, matchId: String): MatchDto =
        matchService.findById(championshipId, matchId)


    override fun getMatches(championshipId: String, pageNumber: Int, pageSize: Int): PageDto<MatchDto> {
        return matchService.findAll(
            championshipId = championshipId,
            pageNumber = pageNumber,
            pageSize = pageSize
        )
    }

}