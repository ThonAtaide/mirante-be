package br.com.mirantebackend.dao

import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.interfaces.MatchDao
import br.com.mirantebackend.dao.usecases.interfaces.match.CreateMatchUseCase
import br.com.mirantebackend.dao.usecases.interfaces.match.FindMatchUseCase
import br.com.mirantebackend.dao.usecases.interfaces.match.UpdateMatchUseCase
import br.com.mirantebackend.model.documents.MatchDocument
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class MatchDaoImpl(
    private val createMatchUseCase: CreateMatchUseCase,
    private val updateMatchUseCase: UpdateMatchUseCase,
    private val findMatchUseCase: FindMatchUseCase,
    mongoTemplate: MongoTemplate
) : AbstractDao(mongoTemplate), MatchDao {

    override fun save(championshipId: String, matchDocument: MatchDocument): MatchDocument =
        createMatchUseCase.createNewMatch(championshipId, matchDocument, mongoTemplate)

    override fun update(
        championshipId: String,
        matchId: String,
        matchDocument: MatchDocument
    ): MatchDocument =
        updateMatchUseCase.update(championshipId, matchId, matchDocument, mongoTemplate)

    override fun findById(championshipId: String, matchId: String): Optional<MatchDocument> =
        findMatchUseCase.findById(championshipId, matchId, mongoTemplate)

    override fun findAll(
        championshipId: String?,
        championshipName: String?,
        season: String?,
        organizedBy: String?,
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
            championshipName,
            season,
            organizedBy,
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
