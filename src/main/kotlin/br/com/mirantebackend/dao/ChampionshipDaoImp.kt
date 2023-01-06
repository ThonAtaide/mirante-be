package br.com.mirantebackend.dao

import br.com.mirantebackend.dao.aggregationDto.pagination.AbstractPaginatedAggregationResultDto
import br.com.mirantebackend.dao.aggregationDto.pagination.ChampionshipPaginatedAggregationResultDto
import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.interfaces.ChampionshipDao
import br.com.mirantebackend.exceptions.ChampionshipCreationException
import br.com.mirantebackend.exceptions.ChampionshipNotFoundException
import br.com.mirantebackend.exceptions.ChampionshipUpdateException
import br.com.mirantebackend.model.documents.ChampionshipDocument
import br.com.mirantebackend.model.documents.ChampionshipDocument.Companion.FIELD_CREATED_AT
import br.com.mirantebackend.model.documents.ChampionshipDocument.Companion.FIELD_ID
import br.com.mirantebackend.model.documents.ChampionshipDocument.Companion.FIELD_NAME
import br.com.mirantebackend.model.documents.ChampionshipDocument.Companion.FIELD_ORGANIZED_BY
import br.com.mirantebackend.model.documents.ChampionshipDocument.Companion.FIELD_SEASON
import br.com.mirantebackend.model.documents.ChampionshipDocument.Companion.FIELD_UPDATED_AT
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.aggregation.ArrayOperators
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.*

@Service
class ChampionshipDaoImp(mongoTemplate: MongoTemplate) : AbstractDao(mongoTemplate), ChampionshipDao {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun save(championshipDocument: ChampionshipDocument): ChampionshipDocument {
        try {
            logger.info { "Persisting new championship document $championshipDocument" }
            return championshipDocument.copy(id = null)
                .let { mongoTemplate.save(it) }
        } catch (err: Exception) {
            logger.error { err.message }
            throw ChampionshipCreationException(err)
        }
    }

    override fun updateNonNestedFields(
        championshipId: String,
        championshipDocument: ChampionshipDocument
    ): ChampionshipDocument {
        try {
            logger.info { "Updating championship non nested fields from document $championshipId" }
            return Query()
                .also { query -> query.addCriteria(Criteria.where(FIELD_ID).`is`(championshipId)) }
                .let { query ->
                    val update = Update()
                    championshipDocument.name.let { update.set(FIELD_NAME, it) }
                    championshipDocument.season.let { update.set(FIELD_SEASON, it) }
                    championshipDocument.organizedBy.let { update.set(FIELD_ORGANIZED_BY, it) }
                    update.set(FIELD_UPDATED_AT, LocalDateTime.now(UTC))

                    val updateResult = mongoTemplate.updateFirst(query, update, ChampionshipDocument::class.java)

                    if (updateResult.matchedCount > 1L)
                        throw ChampionshipUpdateException("The number of championships that matched with id $championshipId was ${updateResult.matchedCount}. However only the first document was updated.")
                    else if (updateResult.matchedCount > 1L)
                        throw ChampionshipUpdateException("No one championships matched with id $championshipId. So neither document was updated.")

                    return@let this.findById(championshipId)
                        .orElseThrow { ChampionshipNotFoundException(championshipId) }
                }
        } catch (err: Exception) {
            logger.error { err.message }
            throw ChampionshipUpdateException(err)
        }
    }

    override fun findById(championshipId: String): Optional<ChampionshipDocument> =
        logger.info { "Fetching championship with id: $championshipId" }
            .let {
                Optional.ofNullable(
                    mongoTemplate.findById(championshipId, ChampionshipDocument::class.java)
                )
            }

    override fun findAll(
        championshipName: String?,
        season: String?,
        organizedBy: String?,
        pageNumber: Int,
        pageSize: Int
    ): Page<ChampionshipDocument> {
        logger.info { "Fetching championship with - Page number: $pageNumber - Page size: $pageSize" }
        return mutableListOf<Criteria>()
            .also { criteriaList ->
                championshipName?.let {
                    criteriaList.add(
                        Criteria.where(FIELD_NAME).regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
                season?.let {
                    criteriaList.add(
                        Criteria.where(FIELD_SEASON).regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
                organizedBy?.let {
                    criteriaList.add(
                        Criteria.where(FIELD_ORGANIZED_BY).regex("^${it}", REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
            }.let { criteriaList ->
                val aggregationOperations = mutableListOf<AggregationOperation>()
                if (criteriaList.isNotEmpty())
                    aggregationOperations.add(Aggregation.match(Criteria().orOperator(criteriaList)))
                return@let aggregationOperations
            }.also { it.add(Aggregation.sort(Sort.by(Sort.Direction.ASC, FIELD_CREATED_AT))) }
            .also {
                it.add(
                    Aggregation.project(
                        FIELD_ID,
                        FIELD_NAME,
                        FIELD_SEASON,
                        FIELD_ORGANIZED_BY,
                        FIELD_CREATED_AT,
                        FIELD_UPDATED_AT
                    )
                )
            }
            .also {
                val facet = Aggregation.facet().and(
                    Aggregation.skip(pageNumber.toLong() * pageSize),
                    Aggregation.limit(pageSize.toLong())
                ).`as`(AbstractPaginatedAggregationResultDto.FIELD_DATA)
                    .and(Aggregation.count().`as`(AbstractPaginatedAggregationResultDto.FIELD_PAGINATION_TOTAL)).`as`(
                        AbstractPaginatedAggregationResultDto.FIELD_PAGINATION
                    )
                it.add(facet)
            }.also {
                val elementAt =
                    ArrayOperators.ArrayElemAt.arrayOf("\$${AbstractPaginatedAggregationResultDto.FIELD_PAGINATION}.${AbstractPaginatedAggregationResultDto.FIELD_PAGINATION_TOTAL}")
                        .elementAt(0)
                it.add(
                    Aggregation.addFields()
                        .addFieldWithValue(AbstractPaginatedAggregationResultDto.FIELD_PAGINATION_TOTAL, elementAt)
                        .build()
                )
            }
            .also {
                it.add(
                    Aggregation.project(
                        AbstractPaginatedAggregationResultDto.FIELD_PAGINATION_TOTAL,
                        AbstractPaginatedAggregationResultDto.FIELD_DATA
                    )
                )
            }.let { aggregationOperations -> Aggregation.newAggregation(aggregationOperations) }
            .let { newAggregation ->
                return@let Optional.ofNullable(
                    mongoTemplate.aggregate(
                        newAggregation,
                        ChampionshipDocument::class.java,
                        ChampionshipPaginatedAggregationResultDto::class.java
                    ).uniqueMappedResult
                ).map { result ->
                    val data = Optional.ofNullable(result.data)
                        .map { it.stream().toList() }
                        .orElse(emptyList())
                    return@map PageImpl<ChampionshipDocument>(
                        data,
                        PageRequest.of(pageNumber, pageSize),
                        result.total
                    )

                }.orElse(PageImpl<ChampionshipDocument>(emptyList(), PageRequest.of(pageNumber, pageSize), 0))
            }
    }
}