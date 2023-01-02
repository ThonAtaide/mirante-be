package br.com.mirantebackend.dao

import br.com.mirantebackend.controller.mappers.toChampionshipDocument
import br.com.mirantebackend.controller.mappers.toChampionshipDto
import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.interfaces.ChampionshipDao
import br.com.mirantebackend.dto.championship.ChampionshipDto
import br.com.mirantebackend.exceptions.ChampionshipCreationException
import br.com.mirantebackend.exceptions.ChampionshipNotFoundException
import br.com.mirantebackend.exceptions.ChampionshipUpdateException
import br.com.mirantebackend.model.ChampionshipDocument
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_CREATED_AT
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_ID
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_NAME
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_ORGANIZED_BY
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_SEASON
import br.com.mirantebackend.model.ChampionshipDocument.Companion.FIELD_UPDATED_AT
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.*
import java.util.stream.Collectors

@Service
class ChampionshipDaoImp(mongoTemplate: MongoTemplate) : AbstractDao(mongoTemplate), ChampionshipDao {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun save(championshipDto: ChampionshipDto): ChampionshipDto {
        try {
            logger.info { "Persisting championship document $championshipDto" }
            return championshipDto.toChampionshipDocument()
                .let { mongoTemplate.save(it) }
                .toChampionshipDto()
        } catch (err: Exception) {
            logger.error { err.message }
            throw ChampionshipCreationException(err)
        }
    }

    override fun updateNonNestedFields(
        championshipId: String,
        championshipDto: ChampionshipDto
    ): ChampionshipDto {
        try {
            logger.info { "Updating championship non nested fields from document $championshipId" }
            return Query()
                .also { query -> query.addCriteria(Criteria.where(FIELD_ID).`is`(championshipId)) }
                .let { query ->
                    val update = Update()
                    championshipDto.name.let { update.set(FIELD_NAME, it) }
                    championshipDto.season.let { update.set(FIELD_SEASON, it) }
                    championshipDto.organizedBy.let { update.set(FIELD_ORGANIZED_BY, it) }
                    update.set(FIELD_UPDATED_AT, LocalDateTime.now(UTC))

                    val updateResult = mongoTemplate.updateFirst(query, update, ChampionshipDocument::class.java)

                    if (updateResult.matchedCount != 1L)
                        throw ChampionshipUpdateException("The number of championships updated was different than expected ${updateResult.matchedCount}")

                    return@let this.findById(championshipId)
                        .orElseThrow { ChampionshipNotFoundException(championshipId) }
                }
        } catch (err: Exception) {
            logger.error { err.message }
            throw ChampionshipUpdateException(err)
        }
    }

    override fun findById(championshipId: String): Optional<ChampionshipDto> =
        logger.info { "Fetching championship with id: $championshipId" }
            .let {
                Optional.ofNullable(
                    mongoTemplate.findById(championshipId, ChampionshipDocument::class.java)
                ).map { it.toChampionshipDto() }
            }

    override fun findAll(
        championshipName: String?,
        season: String?,
        organizedBy: String?,
        pageNumber: Int,
        pageSize: Int
    ): List<ChampionshipDto> {
        logger.info { "Fetching championship with - Page number: $pageNumber - Page size: $pageSize" }
        return Query()
            .also { query -> query.fields().exclude("matches") }
            .also { query -> query.with(PageRequest.of(pageNumber, pageSize)) }
            .also { query -> query.with(Sort.by(Sort.Direction.ASC, FIELD_CREATED_AT)) }
            .also { query ->
                championshipName?.let { query.addCriteria(Criteria.where(FIELD_NAME).regex("^${championshipName}")) }
                season?.let { query.addCriteria(Criteria.where(FIELD_SEASON).regex("^${season}")) }
                organizedBy?.let { query.addCriteria(Criteria.where(FIELD_ORGANIZED_BY).regex("^${organizedBy}")) }
            }.let { query ->
                mongoTemplate.find(query, ChampionshipDocument::class.java)
                    .stream()
                    .map { it.toChampionshipDto() }
                    .collect(Collectors.toList())
            }
    }
}