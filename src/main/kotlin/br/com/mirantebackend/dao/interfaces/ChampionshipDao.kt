package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.model.documents.ChampionshipDocument
import org.springframework.data.domain.Page

interface ChampionshipDao {

    fun findAll(
        championshipName: String? = null,
        season: String? = null,
        organizedBy: String? = null,
        pageNumber: Int,
        pageSize: Int
    ): Page<ChampionshipDocument>
}
