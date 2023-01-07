package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.model.documents.ChampionshipDocument
import org.springframework.data.domain.Page
import java.util.*

interface ChampionshipDao {

    fun save(championshipDocument: ChampionshipDocument): ChampionshipDocument

    fun updateNonNestedFields(championshipId: String, championshipDocument: ChampionshipDocument): ChampionshipDocument

    fun findById(championshipId: String): Optional<ChampionshipDocument>

    fun findAll(
        championshipName: String? = null,
        season: String? = null,
        organizedBy: String? = null,
        pageNumber: Int,
        pageSize: Int
    ): Page<ChampionshipDocument>
}