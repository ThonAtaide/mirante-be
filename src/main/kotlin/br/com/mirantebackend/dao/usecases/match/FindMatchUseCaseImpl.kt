package br.com.mirantebackend.dao.usecases.match

import br.com.mirantebackend.dao.aggregationDto.ChampionshipReducedDto
import br.com.mirantebackend.dao.aggregationDto.pagination.MatchPaginatedAggregationResultDto
import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.usecases.interfaces.match.FindMatchUseCase
import br.com.mirantebackend.dao.utils.AggregationUtils
import br.com.mirantebackend.model.documents.ChampionshipDocument
import br.com.mirantebackend.model.documents.ChampionshipDocument.Companion.FIELD_ID
import br.com.mirantebackend.model.documents.ChampionshipDocument.Companion.FIELD_MATCHES
import br.com.mirantebackend.model.documents.ChampionshipDocument.Companion.FIELD_NAME
import br.com.mirantebackend.model.documents.MatchDocument
import br.com.mirantebackend.model.documents.MatchDocument.Companion.FIELD_PLAYED_AT
import mu.KotlinLogging
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class FindMatchUseCaseImpl : FindMatchUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun findById(
        championshipId: String,
        matchId: String,
        mongoTemplate: MongoTemplate
    ): Optional<MatchDocument> =
        logger.info { "Finding for matchDocument $matchId from championship $championshipId" }
            .let { mutableListOf<AggregationOperation>() }
            .also { it.add(Aggregation.match(Criteria.where(FIELD_ID).`is`(championshipId))) }
            .also { it.add(Aggregation.unwind(FIELD_MATCHES)) }
            .also {
                it.add(
                    Aggregation.match(
                        Criteria.where("$FIELD_MATCHES.${MatchDocument.FIELD_ID}")
                            .`is`(ObjectId(matchId))
                    )
                )
            }
            .also {
                it.add(
                    Aggregation.project(
                        FIELD_ID,
                        FIELD_NAME,
                        FIELD_MATCHES
                    )
                )
            }
            .let { aggregationOperations -> Aggregation.newAggregation(aggregationOperations) }
            .let { newAggregation ->

                return@let Optional.ofNullable(
                    mongoTemplate.aggregate(
                        newAggregation,
                        ChampionshipDocument::class.java,
                        ChampionshipReducedDto::class.java
                    ).uniqueMappedResult
                ).map { championshipDto ->
                    championshipDto.let {
                        it.matches.copy(
                            championship = MatchDocument.ChampionshipInfo(
                                it.id,
                                it.name
                            )
                        )
                    }
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
        pageSize: Int,
        mongoTemplate: MongoTemplate
    ): Page<MatchDocument> {
        return logger.info { "Finding all matches according to filters" }
            .let {
                val criteriaList = mutableListOf<Criteria>()
                championshipId?.let { criteriaList.add(Criteria.where(FIELD_ID).`is`(it)) }
                championshipName?.let {
                    criteriaList.add(
                        Criteria.where(FIELD_NAME).regex(
                            "^$it",
                            AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE
                        )
                    )
                }
                season?.let {
                    criteriaList.add(
                        Criteria.where(ChampionshipDocument.FIELD_SEASON).regex(
                            "^$it",
                            AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE
                        )
                    )
                }
                organizedBy?.let {
                    criteriaList.add(
                        Criteria.where(ChampionshipDocument.FIELD_ORGANIZED_BY).regex(
                            "^$it",
                            AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE
                        )
                    )
                }
                return@let criteriaList
            }.let { criteriaList ->
                val aggregationOperationList = arrayListOf<AggregationOperation>()
                if (criteriaList.isNotEmpty())
                    aggregationOperationList.add(Aggregation.match(Criteria().orOperator(criteriaList)))
                return@let aggregationOperationList
            }
            .also { it.add(Aggregation.unwind(FIELD_MATCHES)) }
            .also { aggregationOperationList ->
                val criteriaList = mutableListOf<Criteria>()
                principal?.let {
                    criteriaList.add(
                        Criteria.where("$FIELD_MATCHES.${MatchDocument.FIELD_PRINCIPAL_NAME}")
                            .regex("^$it", AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
                challenger?.let {
                    criteriaList.add(
                        Criteria.where("$FIELD_MATCHES.${MatchDocument.FIELD_CHALLENGER_NAME}")
                            .regex("^$it", AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
                field?.let {
                    criteriaList.add(
                        Criteria.where("$FIELD_MATCHES.${MatchDocument.FIELD_FIELD}").regex(
                            "^$it",
                            AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE
                        )
                    )
                }
//                playedAt?.let { criteriaList.add(Criteria.where("$FIELD_MATCHES.$FIELD_PLAYED_AT").`is`(it)) }
                matchEnded?.let {
                    criteriaList.add(
                        Criteria.where("$FIELD_MATCHES.${MatchDocument.FIELD_MATCH_ENDED}")
                            .regex("^$it", AbstractDao.REGEX_OPTIONS_CASE_INSENSITIVE)
                    )
                }
                if (criteriaList.isNotEmpty())
                    aggregationOperationList.add(Aggregation.match(Criteria().orOperator(criteriaList)))
            }
            .also {
                it.add(
                    Aggregation.sort(Sort.by(Sort.Direction.ASC, "$FIELD_MATCHES.$FIELD_PLAYED_AT"))
                )
            }
            .also { it.add(Aggregation.project(FIELD_ID, FIELD_NAME, FIELD_MATCHES)) }
            .let { AggregationUtils.buildNewPaginatedAggregation(pageNumber, pageSize, it) }
            .let { newAggregation ->

                return@let Optional.ofNullable(
                    mongoTemplate.aggregate(
                        newAggregation,
                        ChampionshipDocument::class.java,
                        MatchPaginatedAggregationResultDto::class.java
                    ).uniqueMappedResult
                ).map { result ->
                    val data = Optional.ofNullable(result.data)
                        .map {
                            it.stream().map { championship ->
                                championship.matches.copy(
                                    championship = MatchDocument.ChampionshipInfo(
                                        championship.id,
                                        championship.name
                                    )
                                )
                            }.toList()
                        }
                        .orElse(emptyList())
                    PageImpl<MatchDocument>(data, PageRequest.of(pageNumber, pageSize), result.total)
                }.orElse(PageImpl<MatchDocument>(emptyList(), PageRequest.of(pageNumber, pageSize), 0))
            }
    }
}
