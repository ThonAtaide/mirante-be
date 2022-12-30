package br.com.mirantebackend.dao

import br.com.mirantebackend.controller.mappers.toMatchDocument
import br.com.mirantebackend.controller.mappers.toMatchDto
import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.interfaces.MatchDao
import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.exceptions.ChampionshipNotFoundException
import br.com.mirantebackend.exceptions.MatchCreationException
import br.com.mirantebackend.model.ChampionshipDocument
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_ID
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_MATCHES
import br.com.mirantebackend.model.MatchDocument
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.*

@Service
class MatchDaoImpl(mongoTemplate: MongoTemplate) : AbstractDao(mongoTemplate), MatchDao {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun save(championshipId: String, matchDto: MatchDto): MatchDto {
        logger.info { "Creating new match into championship $championshipId" }

        validateIfChampionshipExists(championshipId)
        val objectId = ObjectId()
        pushNewMatchDocument(championshipId, objectId, matchDto)

        return this.findById(championshipId, objectId.toString())
            .orElseThrow { MatchCreationException("Match does not throw error on creation but was not found") }
    }

    override fun update(championshipId: String, matchId: String, matchDto: MatchDto): MatchDto {
        TODO("Not yet implemented")
    }

    override fun findById(championshipId: String, matchId: String): Optional<MatchDto> =
        logger.info { "Finding for matchDocument $matchId from championship $championshipId" }
            .let { mutableListOf<AggregationOperation>() }
            .also { it.add(Aggregation.match(Criteria.where(FIELD_ID).`is`(championshipId))) }
            .also { it.add(Aggregation.unwind(FIELD_MATCHES)) }
            .also { it.add(Aggregation.match(Criteria.where("matches._id").`is`(ObjectId(matchId)))) }
            .also { it.add(Aggregation.project(FIELD_MATCHES).andExclude(FIELD_ID)) }
            .also { it.add(Aggregation.replaceRoot(FIELD_MATCHES)) }
            .let { aggregationOperations -> Aggregation.newAggregation(aggregationOperations) }
            .let { newAggregation ->

                return@let Optional.ofNullable(
                    mongoTemplate.aggregate(
                        newAggregation,
                        "coll_championship",
                        MatchDocument::class.java
                    ).uniqueMappedResult
                ).map { matchDocument -> matchDocument.toMatchDto() }
            }


    override fun findAllMatchesFromChampionship(
        championshipName: String?,
        season: String?,
        organizedBy: String?,
        pageNumber: Int,
        pageSize: Int
    ): List<MatchDto> {
        TODO("Not yet implemented")
    }

    override fun findAllMatches(
        championshipName: String?,
        season: String?,
        organizedBy: String?,
        pageNumber: Int,
        pageSize: Int
    ): List<MatchDto> {
        TODO("Not yet implemented")
    }

    fun validateIfChampionshipExists(championshipId: String) {
        if (mongoTemplate.findById(championshipId, ChampionshipDocument::class.java) == null)
            throw ChampionshipNotFoundException(championshipId)
    }

    fun pushNewMatchDocument(championshipId: String, matchObjectId: ObjectId, matchDto: MatchDto): Unit =
        logger.info { "Pushing new match document with object id $matchObjectId" }
            .let { Query() }
            .also { it.addCriteria(Criteria.where(FIELD_ID).`is`(championshipId)) }
            .let { query ->
                matchDto.id = matchObjectId.toString()
                matchDto.createdAt = LocalDateTime.now(UTC)
                val update = Update()
                update.push(FIELD_MATCHES, matchDto.toMatchDocument())
                return@let mongoTemplate.updateFirst(query, update, ChampionshipDocument::class.java)
            }.let { if (it.matchedCount != 1L) throw MatchCreationException(matchDto) }
}