package br.com.mirantebackend.dao.usecases.interfaces.match

import br.com.mirantebackend.model.documents.MatchDocument
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import java.util.Optional

interface FindMatchUseCase {

    fun findAll(
        championshipId: String? = null,
        principal: String? = null,
        challenger: String? = null,
        field: String? = null,
        playedAt: String? = null,
        matchEnded: Boolean? = null,
        pageNumber: Int,
        pageSize: Int,
        mongoTemplate: MongoTemplate
    ): Page<MatchDocument>
}
