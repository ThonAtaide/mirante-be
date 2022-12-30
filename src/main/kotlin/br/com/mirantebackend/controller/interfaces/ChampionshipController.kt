package br.com.mirantebackend.controller.interfaces

import br.com.mirantebackend.dto.championship.ChampionshipDto
import br.com.mirantebackend.dto.pageable.PageDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RequestMapping("/championship")
@Tag(name = "ChampionshipController", description = "Gerenciamento de campeonatos disputados pela equipe.")
interface ChampionshipController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createChampionship(@RequestBody championship: ChampionshipDto): ChampionshipDto

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{championship-id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateChampionship(
        @PathVariable("championship-id") championshipId: String,
        @RequestBody championship: ChampionshipDto
    ): ChampionshipDto

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{championship-id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getChampionship(
        @PathVariable("championship-id") championshipId: String
    ): ChampionshipDto

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getChampionships(
        @RequestParam("name") name: String?,
        @RequestParam(defaultValue = "0") pageNumber: Int = 0,
        @RequestParam(defaultValue = "10") pageSize: Int = 10
    ): PageDto<ChampionshipDto>


}