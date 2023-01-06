package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.dto.championship.ChampionshipDto
import org.springframework.data.domain.Page
import java.util.*

interface ChampionshipDao {

    fun save(championshipDto: ChampionshipDto): ChampionshipDto

    fun updateNonNestedFields(championshipId: String, championshipDto: ChampionshipDto): ChampionshipDto

    fun findById(championshipId: String): Optional<ChampionshipDto>

    fun findAll(
        championshipName: String?  = null,
        season: String? = null,
        organizedBy: String? = null,
        pageNumber: Int,
        pageSize: Int

    ): Page<ChampionshipDto>
}