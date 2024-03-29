package br.com.mirantebackend.controller.interfaces

import br.com.mirantebackend.controller.vo.championship.ChampionshipVo
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

@RequestMapping("/championship")
@Tag(name = "ChampionshipController", description = "Gerenciamento de campeonatos disputados pela equipe.")
interface ChampionshipController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createChampionship(@Validated @RequestBody championship: ChampionshipVo): ChampionshipVo

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{championship-id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateChampionship(
        @PathVariable("championship-id") championshipId: String,
        @Validated @RequestBody championship: ChampionshipVo
    ): ChampionshipVo

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{championship-id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getChampionship(
        @PathVariable("championship-id") championshipId: String
    ): ChampionshipVo

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getChampionships(
        @RequestParam("name") name: String?,
        @RequestParam(defaultValue = "0") pageNumber: Int = 0,
        @RequestParam(defaultValue = "10") pageSize: Int = 10
    ): PageDto<ChampionshipVo>
}
