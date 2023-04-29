package br.com.mirantebackend.dao.usecases.match

import br.com.mirantebackend.dao.aggregationDto.pagination.MatchPaginatedAggregationResultDto
import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.usecases.interfaces.match.FindMatchUseCase
import br.com.mirantebackend.dao.utils.AggregationUtils
import br.com.mirantebackend.model.documents.MatchDocument
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_CHALLENGER
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_CHALLENGER_NAME
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_CHAMPIONSHIP_ID
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_CREATED_AT
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_FIELD
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_ID
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_MATCH_ENDED
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_PLAYED_AT
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_PRINCIPAL
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_PRINCIPAL_NAME
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_UPDATED_AT
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import java.util.*

@Service
class FindMatchUseCaseImpl : FindMatchUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun findAll(
        championshipId: String?,
        principal: String?,
        challenger: String?,
        field: String?,
        playedAt: String?,
        matchEnded: Boolean?,
        pageNumber: Int,
        pageSize: Int,
        mongoTemplate: MongoTemplate
    ): Page<MatchDocument> {
        return logger.info { "Finding all matches according to filters" }
            .let {
                val criteriaList = mutableListOf<Criteria>()
                championshipId?.let { criteriaList.add(Criteria.where(FIELD_CHAMPIONSHIP_ID).`is`(it)) }
                principal?.let {
                    criteriaList.add(
                        Criteria.where(FIELD_PRINCIPAL_NAME).`is`(it)
                            .regex("^$it", AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
                challenger?.let {
                    criteriaList.add(
                        Criteria.where(FIELD_CHALLENGER_NAME).`is`(it)
                            .regex("^$it", AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
                field?.let {
                    criteriaList.add(
                        Criteria.where(FIELD_FIELD).`is`(it)
                            .regex("^$it", AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
                field?.let {
                    criteriaList.add(
                        Criteria.where(FIELD_MATCH_ENDED).`is`(it)
                            .regex("^$it", AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
                return@let criteriaList
            }.let { criteriaList ->
                val aggregationOperationList = arrayListOf<AggregationOperation>()
                if (criteriaList.isNotEmpty())
                    aggregationOperationList.add(Aggregation.match(Criteria().orOperator(criteriaList)))
                return@let aggregationOperationList
            }.also {
                it.add(
                    Aggregation.sort(Sort.by(Sort.Direction.ASC, FIELD_PLAYED_AT))
                )
            }
            .also {
                it.add(
                    Aggregation.project(
                        FIELD_ID,
                        FIELD_FIELD,
                        FIELD_PLAYED_AT,
                        FIELD_PRINCIPAL,
                        FIELD_CHALLENGER,
                        FIELD_CREATED_AT,
                        FIELD_UPDATED_AT,
                        "championship"
                    )
                )
            }
            .let { AggregationUtils.buildNewPaginatedAggregation(pageNumber, pageSize, it) }
            .let { newAggregation ->

                return@let Optional.ofNullable(
                    mongoTemplate.aggregate(
                        newAggregation,
                        MatchDocument::class.java,
                        MatchPaginatedAggregationResultDto::class.java
                    ).uniqueMappedResult
                ).map { result ->
                    val data = Optional.ofNullable(result.data)
                        .map { it.stream().toList() }
                        .orElse(emptyList())
                    PageImpl<MatchDocument>(data, PageRequest.of(pageNumber, pageSize), result.total)
                }.orElse(PageImpl<MatchDocument>(emptyList(), PageRequest.of(pageNumber, pageSize), 0))
            }
    }
}
