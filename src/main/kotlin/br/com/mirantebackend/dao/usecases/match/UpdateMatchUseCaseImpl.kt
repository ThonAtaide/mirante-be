package br.com.mirantebackend.dao.usecases.match

import br.com.mirantebackend.dao.usecases.interfaces.match.FindMatchUseCase
import br.com.mirantebackend.dao.usecases.interfaces.match.UpdateMatchUseCase
import br.com.mirantebackend.exceptions.MatchNotFoundException
import br.com.mirantebackend.exceptions.MatchUpdateException
import br.com.mirantebackend.model.documents.ChampionshipDocument
import br.com.mirantebackend.model.documents.MatchDocument
import mu.KotlinLogging
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class UpdateMatchUseCaseImpl(private val findMatchUseCase: FindMatchUseCase) : UpdateMatchUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun update(
        championshipId: String,
        matchId: String,
        matchDocument: MatchDocument,
        mongoTemplate: MongoTemplate
    ): MatchDocument =
        logger.info { "Updating match with id $matchId from $championshipId" }
            .let {
                Query(
                    Criteria.where(ChampionshipDocument.FIELD_ID)
                        .`is`(championshipId)
                        .andOperator(
                            Criteria.where(ChampionshipDocument.FIELD_MATCHES)
                                .elemMatch(Criteria.where(MatchDocument.FIELD_ID).`is`(matchId))
                        )
                )
            }.let { query ->

                return@let mongoTemplate.updateFirst(
                    query,
                    buildMatchUpdate(matchDocument.copy(id = matchId, updatedAt = LocalDateTime.now(ZoneOffset.UTC))),
                    ChampionshipDocument::class.java
                )
            }.let {
                if (it.modifiedCount != 1L) throw MatchUpdateException(matchDocument)
                return@let findMatchUseCase.findById(championshipId, matchId, mongoTemplate)
                    .orElseThrow { MatchNotFoundException(matchId, championshipId) }
            }

    private fun buildMatchUpdate(matchDocument: MatchDocument): Update =
        Update().apply {
            set("${ChampionshipDocument.FIELD_MATCHES}.$.${MatchDocument.FIELD_FIELD}", matchDocument.field)
            set("${ChampionshipDocument.FIELD_MATCHES}.$.${MatchDocument.FIELD_PLAYED_AT}", matchDocument.playedAt)
            set("${ChampionshipDocument.FIELD_MATCHES}.$.${MatchDocument.FIELD_PRINCIPAL}", matchDocument.principal)
            set("${ChampionshipDocument.FIELD_MATCHES}.$.${MatchDocument.FIELD_CHALLENGER}", matchDocument.challenger)
            set("${ChampionshipDocument.FIELD_MATCHES}.$.${MatchDocument.FIELD_UPDATED_AT}", matchDocument.updatedAt)
            set("${ChampionshipDocument.FIELD_MATCHES}.$.${MatchDocument.FIELD_MATCH_ENDED}", matchDocument.matchEnded)
        }
}
