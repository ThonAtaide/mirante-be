package br.com.mirantebackend.dao.usecases.championship

import br.com.mirantebackend.dao.usecases.interfaces.championship.CreateChampionshipUseCase
import br.com.mirantebackend.exceptions.ChampionshipCreationException
import br.com.mirantebackend.model.documents.ChampionshipDocument
import mu.KotlinLogging
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class CreateChampionshipUseCaseImpl : CreateChampionshipUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createChampionship(
        championshipDocument: ChampionshipDocument,
        mongoTemplate: MongoTemplate
    ): ChampionshipDocument {
        try {
            logger.info { "Persisting new championship document $championshipDocument" }
            return championshipDocument.copy(id = null)
                .let { mongoTemplate.save(it) }
        } catch (err: Exception) {
            logger.error { err.message }
            throw ChampionshipCreationException(err)
        }
    }
}
