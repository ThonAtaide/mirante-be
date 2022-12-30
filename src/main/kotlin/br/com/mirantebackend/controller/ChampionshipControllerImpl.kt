package br.com.mirantebackend.controller

import br.com.mirantebackend.controller.interfaces.ChampionshipController
import br.com.mirantebackend.dto.championship.ChampionshipDto
import br.com.mirantebackend.dto.pageable.PageDto
import br.com.mirantebackend.services.interfaces.ChampionshipService
import org.springframework.web.bind.annotation.RestController

@RestController
class ChampionshipControllerImpl(
    private val championshipService: ChampionshipService
) : ChampionshipController {

    override fun createChampionship(championship: ChampionshipDto): ChampionshipDto =
        championshipService.createChampionship(championship)

    override fun updateChampionship(championshipId: String, championship: ChampionshipDto): ChampionshipDto =
        championshipService.updateChampionship(championshipId, championship)

    override fun getChampionship(championshipId: String): ChampionshipDto =
        championshipService.getChampionship(championshipId)

    override fun getChampionships(name: String?, pageNumber: Int, pageSize: Int): PageDto<ChampionshipDto> =
        championshipService.getChampionships(name, pageNumber, pageSize)
}