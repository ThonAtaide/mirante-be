package br.com.mirantebackend.controller.interfaces

import br.com.mirantebackend.controller.vo.PageDto
import br.com.mirantebackend.services.dto.matches.MatchDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/match")
@Tag(name = "MatchController", description = "Operations about matches on team calendar")
interface MatchController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMatches(@RequestParam pageSize: Long? = 10, @RequestParam pageNumber: Long? = 0): PageDto

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{matchId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getMatch(@PathVariable("matchId") matchId: String): MatchDto

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun registerMatch(@RequestBody match: MatchDto): MatchDto

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{matchId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateMatch(@PathVariable("matchId") matchId: String, @RequestBody match: MatchDto): MatchDto
}