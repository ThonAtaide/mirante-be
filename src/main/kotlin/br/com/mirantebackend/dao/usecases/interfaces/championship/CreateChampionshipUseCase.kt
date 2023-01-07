package br.com.mirantebackend.dao.usecases.interfaces.championship

import br.com.mirantebackend.model.documents.ChampionshipDocument
import org.springframework.data.mongodb.core.MongoTemplate

interface CreateChampionshipUseCase {

    fun createChampionship(
        championshipDocument: ChampionshipDocument,
        mongoTemplate: MongoTemplate
    ): ChampionshipDocument
}