package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.model.dto.championship.ChampionshipDto
import br.com.mirantebackend.model.dto.pageable.PageDto

interface ChampionshipService {

    fun createChampionship(championship: ChampionshipDto): ChampionshipDto

    fun updateChampionship(championshipId: String, championship: ChampionshipDto): ChampionshipDto

    fun getChampionship(championshipId: String): ChampionshipDto

    fun getChampionships(name: String?, pageNumber: Int, pageSize: Int): PageDto<ChampionshipDto>
}