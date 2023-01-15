package br.com.mirantebackend.dao.usecases.interfaces.championship

import br.com.mirantebackend.model.documents.ChampionshipDocument
import org.springframework.data.mongodb.core.MongoTemplate

interface UpdateChampionshipUseCase {

    fun updateNonNestedFields(
        championshipId: String,
        championshipDocument: ChampionshipDocument,
        mongoTemplate: MongoTemplate
    ): ChampionshipDocument
}
