package br.com.mirantebackend.dao.usecases.championship

import br.com.mirantebackend.dao.aggregationDto.pagination.ChampionshipPaginatedAggregationResultDto
import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.usecases.interfaces.championship.FindChampionshipUseCase
import br.com.mirantebackend.dao.utils.AggregationUtils
import br.com.mirantebackend.model.documents.ChampionshipDocument
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
class FindChampionshipUseCaseImpl : FindChampionshipUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun findById(championshipId: String, mongoTemplate: MongoTemplate): Optional<ChampionshipDocument> =
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
        pageSize: Int,
        mongoTemplate: MongoTemplate
    ): Page<ChampionshipDocument> {
        logger.info { "Fetching championship with - Page number: $pageNumber - Page size: $pageSize" }
        return mutableListOf<Criteria>()
            .also { criteriaList ->
                championshipName?.let {
                    criteriaList.add(
                        Criteria.where(ChampionshipDocument.FIELD_NAME).regex(
                            "^${it}",
                            AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE
                        )
                    )
                }
                season?.let {
                    criteriaList.add(
                        Criteria.where(ChampionshipDocument.FIELD_SEASON).regex(
                            "^${it}",
                            AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE
                        )
                    )
                }
                organizedBy?.let {
                    criteriaList.add(
                        Criteria.where(ChampionshipDocument.FIELD_ORGANIZED_BY).regex(
                            "^${it}",
                            AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE
                        )
                    )
                }
            }.let { criteriaList ->
                val aggregationOperations = mutableListOf<AggregationOperation>()
                if (criteriaList.isNotEmpty())
                    aggregationOperations.add(Aggregation.match(Criteria().orOperator(criteriaList)))
                return@let aggregationOperations
            }.also { it.add(Aggregation.sort(Sort.by(Sort.Direction.ASC, ChampionshipDocument.FIELD_CREATED_AT))) }
            .also {
                it.add(
                    Aggregation.project(
                        ChampionshipDocument.FIELD_ID,
                        ChampionshipDocument.FIELD_NAME,
                        ChampionshipDocument.FIELD_SEASON,
                        ChampionshipDocument.FIELD_ORGANIZED_BY,
                        ChampionshipDocument.FIELD_CREATED_AT,
                        ChampionshipDocument.FIELD_UPDATED_AT
                    )
                )
            }.let { AggregationUtils.buildNewPaginatedAggregation(pageNumber, pageSize, it) }
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