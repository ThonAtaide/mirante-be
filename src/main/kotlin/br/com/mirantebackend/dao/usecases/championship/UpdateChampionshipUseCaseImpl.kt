package br.com.mirantebackend.dao.usecases.championship

import br.com.mirantebackend.dao.usecases.interfaces.championship.FindChampionshipUseCase
import br.com.mirantebackend.dao.usecases.interfaces.championship.UpdateChampionshipUseCase
import br.com.mirantebackend.exceptions.ChampionshipNotFoundException
import br.com.mirantebackend.exceptions.ChampionshipUpdateException
import br.com.mirantebackend.model.documents.ChampionshipDocument
import mu.KotlinLogging
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class UpdateChampionshipUseCaseImpl(
    private val findChampionshipUseCase: FindChampionshipUseCase
) : UpdateChampionshipUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun updateNonNestedFields(
        championshipId: String,
        championshipDocument: ChampionshipDocument,
        mongoTemplate: MongoTemplate
    ): ChampionshipDocument {
        try {
            logger.info { "Updating championship non nested fields from document $championshipId" }
            return Query()
                .also { query -> query.addCriteria(Criteria.where(ChampionshipDocument.FIELD_ID).`is`(championshipId)) }
                .let { query ->
                    val update = Update()
                    championshipDocument.name.let { update.set(ChampionshipDocument.FIELD_NAME, it) }
                    championshipDocument.season.let { update.set(ChampionshipDocument.FIELD_SEASON, it) }
                    championshipDocument.organizedBy.let { update.set(ChampionshipDocument.FIELD_ORGANIZED_BY, it) }
                    update.set(ChampionshipDocument.FIELD_UPDATED_AT, LocalDateTime.now(ZoneOffset.UTC))

                    val updateResult = mongoTemplate.updateFirst(query, update, ChampionshipDocument::class.java)

                    if (updateResult.matchedCount > 1L)
                        throw ChampionshipUpdateException("The number of championships that matched with id $championshipId was ${updateResult.matchedCount}. However only the first document was updated.")
                    else if (updateResult.matchedCount > 1L)
                        throw ChampionshipUpdateException("No one championships matched with id $championshipId. So neither document was updated.")

                    return@let findChampionshipUseCase.findById(championshipId, mongoTemplate)
                        .orElseThrow { ChampionshipNotFoundException(championshipId) }
                }
        } catch (err: Exception) {
            logger.error { err.message }
            throw ChampionshipUpdateException(err)
        }
    }
}