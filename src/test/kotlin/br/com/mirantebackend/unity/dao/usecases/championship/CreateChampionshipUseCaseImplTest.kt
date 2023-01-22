package br.com.mirantebackend.unity.dao.usecases.championship

import br.com.mirantebackend.dao.usecases.championship.CreateChampionshipUseCaseImpl
import br.com.mirantebackend.exceptions.ChampionshipCreationException
import br.com.mirantebackend.factories.ChampionshipDocumentFactory
import br.com.mirantebackend.unity.AbstractUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.data.mongodb.core.MongoTemplate

class CreateChampionshipUseCaseImplTest : AbstractUnitTest() {

    @Mock
    private lateinit var mongoTemplate: MongoTemplate

    private val createChampionshipUseCase = CreateChampionshipUseCaseImpl()

    @Test
    fun `given a new championship - when it is persisted successfully - then method should return the entity with id`() {
        val championshipDocumentBeforePersist = ChampionshipDocumentFactory.buildWith(id = null)
        val championshipDocumentCreated = championshipDocumentBeforePersist.copy(id = ObjectId().toString())

        `when`(mongoTemplate.save(eq(championshipDocumentBeforePersist))).thenReturn(championshipDocumentCreated)

        createChampionshipUseCase.createChampionship(championshipDocumentBeforePersist, mongoTemplate)
            .let {
                assertThat(it).usingRecursiveComparison().isEqualTo(championshipDocumentCreated)
            }
    }

    @Test
    fun `given that mongoTemplate save method throws an error - when the error is captured - then it is casted to ChampionshipCreationException`() {
        val championshipDocumentBeforePersist = ChampionshipDocumentFactory.buildWith(id = null)
        val errorMessage = "Database unavailable"
        `when`(mongoTemplate.save(eq(championshipDocumentBeforePersist))).thenThrow(RuntimeException(errorMessage))

        assertThrows<ChampionshipCreationException>(
            errorMessage
        ) {
            createChampionshipUseCase.createChampionship(
                championshipDocumentBeforePersist,
                mongoTemplate
            )
        }
    }
}
