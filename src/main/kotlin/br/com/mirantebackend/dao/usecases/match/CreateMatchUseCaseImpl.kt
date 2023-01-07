package br.com.mirantebackend.dao.usecases.match

import br.com.mirantebackend.dao.usecases.interfaces.match.CreateMatchUseCase
import br.com.mirantebackend.dao.usecases.interfaces.match.FindMatchUseCase
import br.com.mirantebackend.exceptions.MatchCreationException
import br.com.mirantebackend.model.documents.ChampionshipDocument
import br.com.mirantebackend.model.documents.MatchDocument
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class CreateMatchUseCaseImpl(
    private val findMatchUseCase: FindMatchUseCase
) : CreateMatchUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createNewMatch(
        championshipId: String,
        matchDocument: MatchDocument,
        mongoTemplate: MongoTemplate
    ): MatchDocument {
        logger.info { "Creating new match into championship $championshipId" }

        val objectId = ObjectId()
        pushNewMatchDocument(championshipId, objectId, matchDocument, mongoTemplate)

        return findMatchUseCase.findById(championshipId, objectId.toString(), mongoTemplate)
            .orElseThrow { MatchCreationException("Match does not throw error on creation but was not found") }
    }

    private fun pushNewMatchDocument(
        championshipId: String,
        matchObjectId: ObjectId,
        matchDocument: MatchDocument,
        mongoTemplate: MongoTemplate
    ): Unit =
        logger.info { "Pushing new match document with object id $matchObjectId" }
            .let { Query() }
            .also { it.addCriteria(Criteria.where(ChampionshipDocument.FIELD_ID).`is`(championshipId)) }
            .let { query ->
                val update = Update()
                update.push(
                    ChampionshipDocument.FIELD_MATCHES,
                    matchDocument.copy(id = matchObjectId.toString(), createdAt = LocalDateTime.now(ZoneOffset.UTC))
                )
                return@let mongoTemplate.updateFirst(query, update, ChampionshipDocument::class.java)
            }.let {
                if (it.matchedCount == 0L) throw MatchCreationException("Championship $championshipId does not exist and the match could not be created.")
                if (it.matchedCount > 1L) throw MatchCreationException("More than one Championship has matched with $championshipId but only the first was update with new match.")
            }
}