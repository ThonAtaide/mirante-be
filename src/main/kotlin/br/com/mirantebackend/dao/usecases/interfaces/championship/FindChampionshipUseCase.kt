package br.com.mirantebackend.dao.usecases.interfaces.championship

import br.com.mirantebackend.model.documents.ChampionshipDocument
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import java.util.Optional

interface FindChampionshipUseCase {

    fun findById(championshipId: String, mongoTemplate: MongoTemplate): Optional<ChampionshipDocument>

    fun findAll(
        championshipName: String? = null,
        season: String? = null,
        organizedBy: String? = null,
        pageNumber: Int,
        pageSize: Int,
        mongoTemplate: MongoTemplate
    ): Page<ChampionshipDocument>
}
