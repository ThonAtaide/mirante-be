package br.com.mirantebackend.controller

import br.com.mirantebackend.controller.interfaces.ChampionshipController
import br.com.mirantebackend.controller.mappers.toChampionshipDto
import br.com.mirantebackend.controller.mappers.toChampionshipVo
import br.com.mirantebackend.controller.vo.championship.ChampionshipVo
import br.com.mirantebackend.model.dto.pageable.PageDto
import br.com.mirantebackend.services.interfaces.ChampionshipService
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
class ChampionshipControllerImpl(
    private val championshipService: ChampionshipService
) : ChampionshipController {

    override fun createChampionship(championship: ChampionshipVo): ChampionshipVo =
        championship.toChampionshipDto()
            .let { championshipService.createChampionship(it) }
            .toChampionshipVo()

    override fun updateChampionship(championshipId: String, championship: ChampionshipVo): ChampionshipVo =
        championship.toChampionshipDto()
            .let { championshipService.updateChampionship(championshipId, it) }
            .toChampionshipVo()

    override fun getChampionship(championshipId: String): ChampionshipVo =
        championshipService.getChampionship(championshipId).toChampionshipVo()

    override fun getChampionships(name: String?, pageNumber: Int, pageSize: Int): PageDto<ChampionshipVo> =
        championshipService.getChampionships(name, pageNumber, pageSize)
            .let { pageable ->
                val data = Optional.ofNullable(pageable.records)
                    .map { content ->
                        content.stream()
                            .map { it.toChampionshipVo() }.toList()
                    }.orElse(emptyList())
                return@let PageDto<ChampionshipVo>(pageable.pageSize, pageable.pageNumber, pageable.total, data)
            }
}
