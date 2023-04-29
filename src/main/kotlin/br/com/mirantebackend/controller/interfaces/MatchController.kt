package br.com.mirantebackend.controller.interfaces

import br.com.mirantebackend.controller.vo.match.MatchVo
import br.com.mirantebackend.model.dto.pageable.PageDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalDateTime

@RequestMapping
@Tag(name = "MatchController", description = "Gerenciamento de partidas.")
interface MatchController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/championship/{championship-id}/match", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createMatch(
        @PathVariable("championship-id") championshipId: String,
        @Validated @RequestBody match: MatchVo
    ): MatchVo

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/championship/{championship-id}/match", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMatchesFromChampionship(
        @PathVariable("championship-id") championshipId: String,
        @RequestParam(name = "principal") principal: String?,
        @RequestParam(name = "challenger") challenger: String?,
        @RequestParam(name = "field") field: String?,
        @RequestParam(name = "playedAtAfter") playedAtAfter: LocalDateTime?,
        @RequestParam(name = "playedAtBefore") playedAtBefore: LocalDateTime?,
        @RequestParam(name = "matchEnded") matchEnded: Boolean?,
        @RequestParam(defaultValue = "0") pageNumber: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): PageDto<MatchVo>

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/match/{matchId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateMatch(
        @PathVariable("matchId") matchId: String,
        @Validated @RequestBody match: MatchVo
    ): MatchVo

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/match", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMatchesFromChampionship(
        @RequestParam(name = "principal") principal: String?,
        @RequestParam(name = "challenger") challenger: String?,
        @RequestParam(name = "field") field: String?,
        @RequestParam(name = "playedAtAfter") playedAtAfter: LocalDateTime?,
        @RequestParam(name = "playedAtBefore") playedAtBefore: LocalDateTime?,
        @RequestParam(name = "matchEnded") matchEnded: Boolean?,
        @RequestParam(defaultValue = "0") pageNumber: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): PageDto<MatchVo>

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/match/{matchId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMatch(
        @PathVariable("matchId") matchId: String
    ): MatchVo
}
