package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.dto.championship.ChampionshipDto
import br.com.mirantebackend.dto.pageable.PageDto
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

@Validated
interface ChampionshipService {

    fun createChampionship(@Valid championship: ChampionshipDto): ChampionshipDto

    fun updateChampionship(championshipId: String, @Valid championship: ChampionshipDto): ChampionshipDto

    fun getChampionship(championshipId: String): ChampionshipDto

    fun getChampionships(name: String?, pageNumber: Int, pageSize: Int): PageDto<ChampionshipDto>
}