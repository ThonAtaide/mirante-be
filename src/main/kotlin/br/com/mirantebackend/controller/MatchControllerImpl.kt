package br.com.mirantebackend.controller

import br.com.mirantebackend.controller.interfaces.MatchController
import br.com.mirantebackend.controller.mappers.toMatchDto
import br.com.mirantebackend.controller.mappers.toMatchVo
import br.com.mirantebackend.controller.vo.match.MatchVo
import br.com.mirantebackend.model.dto.pageable.PageDto
import br.com.mirantebackend.services.interfaces.MatchService
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.Optional

@RestController
class MatchControllerImpl(
    private val matchService: MatchService
) : MatchController {

    override fun createMatch(
        championshipId: String,
        match: MatchVo
    ): MatchVo = match.toMatchDto()
        .let { matchService.createMatch(championshipId, it) }
        .toMatchVo()

    override fun updateMatch(
        matchId: String,
        match: MatchVo
    ): MatchVo = match.toMatchDto()
        .let { matchService.updateMatch(matchId, it) }
        .toMatchVo()

    override fun getMatch(matchId: String): MatchVo =
        matchService.findById(matchId).toMatchVo()

    override fun getMatchesFromChampionship(
        championshipId: String,
        principal: String?,
        challenger: String?,
        field: String?,
        playedAtAfter: LocalDateTime?,
        playedAtBefore: LocalDateTime?,
        matchEnded: Boolean?,
        pageNumber: Int,
        pageSize: Int
    ): PageDto<MatchVo> {
        return matchService.findAll(
            championshipId = championshipId,
            principal = principal,
            challenger = challenger,
            field = field,
            matchEnded = matchEnded,
            pageNumber = pageNumber,
            pageSize = pageSize
        ).let { pageable ->
            val data = Optional.ofNullable(pageable.records)
                .map { content ->
                    content.stream()
                        .map { it.toMatchVo() }.toList()
                }.orElse(emptyList())
            return@let PageDto<MatchVo>(pageable.pageSize, pageable.pageNumber, pageable.total, data)
        }
    }

    override fun getMatchesFromChampionship(
        principal: String?,
        challenger: String?,
        field: String?,
        playedAtAfter: LocalDateTime?,
        playedAtBefore: LocalDateTime?,
        matchEnded: Boolean?,
        pageNumber: Int,
        pageSize: Int
    ): PageDto<MatchVo> {
        return matchService.findAll(
            principal = principal,
            challenger = challenger,
            field = field,
            matchEnded = matchEnded,
            pageNumber = pageNumber,
            pageSize = pageSize
        ).let { pageable ->
            val data = Optional.ofNullable(pageable.records)
                .map { content ->
                    content.stream()
                        .map { it.toMatchVo() }.toList()
                }.orElse(emptyList())
            return@let PageDto<MatchVo>(pageable.pageSize, pageable.pageNumber, pageable.total, data)
        }
    }
}
