package br.com.mirantebackend.controller

import br.com.mirantebackend.controller.vo.PageDto
import br.com.mirantebackend.controller.interfaces.MatchController
import br.com.mirantebackend.services.dto.matches.MatchDto
import br.com.mirantebackend.services.interfaces.MatchService
import org.springframework.web.bind.annotation.RestController

@RestController
class MatchControllerImpl(
    private val matchService: MatchService
) : MatchController {

    override fun registerMatch(match: MatchDto): MatchDto {
         return match.run {
             matchService.createMatch(this)
         }
    }

    override fun updateMatch(matchId: String, match: MatchDto): MatchDto {
        return match.run {
            matchService.updateMatch(matchId, this)
        }
    }

    override fun getMatches(pageSize: Long?, pageNumber: Long?): PageDto {
        return PageDto(0, 0, matchService.findAll())
    }


    override fun getMatch(matchId: String): MatchDto {
        return matchId.let { matchService.findById(it) }
    }
}