package br.com.mirantebackend.dao

import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.interfaces.MatchDao
import br.com.mirantebackend.dao.usecases.interfaces.match.FindMatchUseCase
import br.com.mirantebackend.model.documents.MatchDocument
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class MatchDaoImpl(
    private val findMatchUseCase: FindMatchUseCase,
    mongoTemplate: MongoTemplate
) : AbstractDao(mongoTemplate), MatchDao {

    override fun findAll(
        championshipId: String?,
        principal: String?,
        challenger: String?,
        field: String?,
        playedAt: String?,
        matchEnded: Boolean?,
        pageNumber: Int,
        pageSize: Int
    ): Page<MatchDocument> =
        findMatchUseCase.findAll(
            championshipId,
            principal,
            challenger,
            field,
            playedAt,
            matchEnded,
            pageNumber,
            pageSize,
            mongoTemplate
        )
}
