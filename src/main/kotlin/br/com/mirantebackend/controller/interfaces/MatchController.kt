package br.com.mirantebackend.controller.interfaces

import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.PageDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/championship/{championship-id}/match")
@Tag(name = "MatchController", description = "Gerenciamento de partidas relacionadas a determinado campeonato.")
interface MatchController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun registerMatch(
        @PathVariable("championship-id") championshipId: String,
        @RequestBody match: MatchDto
    ): MatchDto

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{matchId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateMatch(
        @PathVariable("championship-id") championshipId: String,
        @PathVariable("matchId") matchId: String, @RequestBody match: MatchDto
    ): MatchDto

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{matchId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMatch(
        @PathVariable("championship-id") championshipId: String,
        @PathVariable("matchId") matchId: String
    ): MatchDto

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMatches(
        @PathVariable("championship-id") championshipId: String,
        @RequestParam pageNumber: Int = 0,
        @RequestParam pageSize: Int = 10
    ): PageDto<MatchDto>


}