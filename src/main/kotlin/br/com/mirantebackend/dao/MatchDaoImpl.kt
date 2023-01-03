package br.com.mirantebackend.dao

import br.com.mirantebackend.controller.mappers.toMatchDocument
import br.com.mirantebackend.controller.mappers.toMatchDto
import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.interfaces.MatchDao
import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.exceptions.MatchCreationException
import br.com.mirantebackend.exceptions.MatchNotFoundException
import br.com.mirantebackend.exceptions.MatchUpdateException
import br.com.mirantebackend.model.ChampionshipDocument
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_ID
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_MATCHES
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_NAME
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_ORGANIZED_BY
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_SEASON
import br.com.mirantebackend.model.MatchDocument
import br.com.mirantebackend.model.MatchDocument.Companion.FIELD_CHALLENGER
import br.com.mirantebackend.model.MatchDocument.Companion.FIELD_CHALLENGER_NAME
import br.com.mirantebackend.model.MatchDocument.Companion.FIELD_FIELD
import br.com.mirantebackend.model.MatchDocument.Companion.FIELD_MATCH_ENDED
import br.com.mirantebackend.model.MatchDocument.Companion.FIELD_PLAYED_AT
import br.com.mirantebackend.model.MatchDocument.Companion.FIELD_PRINCIPAL
import br.com.mirantebackend.model.MatchDocument.Companion.FIELD_PRINCIPAL_NAME
import br.com.mirantebackend.model.MatchDocument.Companion.FIELD_UPDATED_AT
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.limit
import org.springframework.data.mongodb.core.aggregation.Aggregation.skip
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.*
import java.util.stream.Collectors

@Service
class MatchDaoImpl(mongoTemplate: MongoTemplate) : AbstractDao(mongoTemplate), MatchDao {

    // TODO query por played at

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun save(championshipId: String, matchDto: MatchDto): MatchDto {
        logger.info { "Creating new match into championship $championshipId" }

        val objectId = ObjectId()
        pushNewMatchDocument(championshipId, objectId, matchDto)

        return this.findById(championshipId, objectId.toString())
            .orElseThrow { MatchCreationException("Match does not throw error on creation but was not found") }
    }

    override fun update(championshipId: String, matchId: String, matchDto: MatchDto): MatchDto =
        logger.info { "Updating match with id $matchId from $championshipId" }
            .let {
                Query(
                    Criteria.where(FIELD_ID)
                        .`is`(championshipId)
                        .andOperator(
                            Criteria.where(FIELD_MATCHES)
                                .elemMatch(Criteria.where(MatchDocument.FIELD_ID).`is`(matchId))
                        )
                )
            }.let { query ->
                matchDto.id = matchId
                matchDto.updatedAt = LocalDateTime.now(UTC)
                val matchDocument = matchDto.toMatchDocument()

                return@let mongoTemplate.updateFirst(
                    query,
                    buildMatchUpdate(matchDocument),
                    ChampionshipDocument::class.java
                )
            }.let {
                if (it.modifiedCount != 1L) throw MatchUpdateException(matchDto)
                return@let findById(championshipId, matchId)
                    .orElseThrow { MatchNotFoundException(matchId, championshipId) }
            }

    override fun findById(championshipId: String, matchId: String): Optional<MatchDto> =
        logger.info { "Finding for matchDocument $matchId from championship $championshipId" }
            .let { mutableListOf<AggregationOperation>() }
            .also { it.add(Aggregation.match(Criteria.where(FIELD_ID).`is`(championshipId))) }
            .also { it.add(Aggregation.unwind(FIELD_MATCHES)) }
            .also { it.add(Aggregation.match(Criteria.where("$FIELD_MATCHES.${MatchDocument.FIELD_ID}").`is`(ObjectId(matchId)))) }
            .also { it.add(Aggregation.project(FIELD_ID, FIELD_NAME, FIELD_MATCHES)) }
            .let { aggregationOperations -> Aggregation.newAggregation(aggregationOperations) }
            .let { newAggregation ->

                return@let Optional.ofNullable(
                    mongoTemplate.aggregate(
                        newAggregation,
                        ChampionshipDocument::class.java,
                        ChampionshipDto::class.java
                    ).uniqueMappedResult
                ).map { championshipDto -> championshipDto.let { it.matches.toMatchDto(it.id, it.name) }
                }
            }

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
    ): List<MatchDto> {
        return logger.info { "Finding all  matchs according to filters" }
            .let {
                val criteriaList = mutableListOf<Criteria>()
                championshipId?.let { criteriaList.add(Criteria.where(FIELD_ID).`is`(it)) }
                championshipName?.let { criteriaList.add(Criteria.where(FIELD_NAME).regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)) }
                season?.let { criteriaList.add(Criteria.where(FIELD_SEASON).regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)) }
                organizedBy?.let { criteriaList.add(Criteria.where(FIELD_ORGANIZED_BY).regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)) }
                return@let criteriaList
            }.let { criteriaList ->
                val aggregationOperationList = mutableListOf<AggregationOperation>()
                if (criteriaList.isNotEmpty())
                    aggregationOperationList.add(Aggregation.match(Criteria().orOperator(criteriaList)))
                return@let aggregationOperationList
            }
            .also { it.add(Aggregation.unwind(FIELD_MATCHES)) }
            .also { aggregationOperationList ->
                val criteriaList = mutableListOf<Criteria>()
                principal?.let { criteriaList.add(Criteria.where("$FIELD_MATCHES.$FIELD_PRINCIPAL_NAME").regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)) }
                challenger?.let { criteriaList.add(Criteria.where("$FIELD_MATCHES.$FIELD_CHALLENGER_NAME").regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)) }
                field?.let { criteriaList.add(Criteria.where("$FIELD_MATCHES.$FIELD_FIELD").regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)) }
//                playedAt?.let { criteriaList.add(Criteria.where("$FIELD_MATCHES.$FIELD_PLAYED_AT").`is`(it)) }
                matchEnded?.let { criteriaList.add(Criteria.where("$FIELD_MATCHES.$FIELD_MATCH_ENDED").regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)) }
                if (criteriaList.isNotEmpty())
                    aggregationOperationList.add(Aggregation.match(Criteria().orOperator(criteriaList)))
            }
            .also { it.add(Aggregation.sort(Sort.by(Sort.Direction.ASC, "$FIELD_MATCHES.$FIELD_PLAYED_AT"))) }
            .also { it.add(Aggregation.project(FIELD_ID, FIELD_NAME, FIELD_MATCHES)) }
            .also { it.add(skip(pageNumber.toLong() * pageSize)) }
            .also { it.add(limit(pageSize.toLong())) }
            .let { aggregationOperations -> Aggregation.newAggregation(aggregationOperations) }
            .let { newAggregation ->

                return@let Optional.ofNullable(
                    mongoTemplate.aggregate(
                        newAggregation,
                        ChampionshipDocument::class.java,
                        ChampionshipDto::class.java
                    ).mappedResults
                ).map { matchList ->
                    matchList
                        .stream()
                        .map { it.let { it.matches.toMatchDto(it.id, it.name) } }
                        .collect(Collectors.toList())
                }.orElse(emptyList())
            }
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
            }.let {
                if (it.matchedCount == 0L) throw MatchCreationException("Championship $championshipId does not exist and the match could not be created.")
                if (it.matchedCount > 1L) throw MatchCreationException("More than one Championship has matched with $championshipId but only the first was update with new match.")
            }

    protected fun buildMatchUpdate(matchDocument: MatchDocument): Update =
        Update().apply {
            set("$FIELD_MATCHES.$.$FIELD_FIELD", matchDocument.field)
            set("$FIELD_MATCHES.$.$FIELD_PLAYED_AT", matchDocument.playedAt)
            set("$FIELD_MATCHES.$.$FIELD_PRINCIPAL", matchDocument.principal)
            set("$FIELD_MATCHES.$.$FIELD_CHALLENGER", matchDocument.challenger)
            set("$FIELD_MATCHES.$.$FIELD_UPDATED_AT", matchDocument.updatedAt)
            set("$FIELD_MATCHES.$.$FIELD_MATCH_ENDED", matchDocument.matchEnded)
        }

    data class ChampionshipDto(
        var id: String? = null,
        var name: String? = null,
        var matches: MatchDocument
    )
}