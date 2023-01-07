package br.com.mirantebackend.dao.usecases.interfaces.match

import br.com.mirantebackend.model.documents.MatchDocument
import org.springframework.data.mongodb.core.MongoTemplate

interface UpdateMatchUseCase {

    fun update(
        championshipId: String,
        matchId: String,
        matchDocument: MatchDocument,
        mongoTemplate: MongoTemplate
    ): MatchDocument
}