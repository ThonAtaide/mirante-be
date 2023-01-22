package br.com.mirantebackend.unity.dao.usecases.championship

import br.com.mirantebackend.dao.usecases.championship.FindChampionshipUseCaseImpl
import br.com.mirantebackend.factories.ChampionshipDocumentFactory
import br.com.mirantebackend.model.documents.ChampionshipDocument
import br.com.mirantebackend.unity.AbstractUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.data.mongodb.core.MongoTemplate

class FindChampionshipUseCaseImplTest : AbstractUnitTest() {

    @Mock
    private lateinit var mongoTemplate: MongoTemplate

    private val findChampionshipUseCase = FindChampionshipUseCaseImpl()

    @Test
    fun `given a valid championship id - when database return championship - Then method returns a optional filled`() {
        val championshipId = ObjectId().toString()
        val championshipDocument = ChampionshipDocumentFactory.buildWith(id = championshipId)
        Mockito.`when`(mongoTemplate.findById(eq(championshipId), eq(ChampionshipDocument::class.java)))
            .thenReturn(championshipDocument)

        findChampionshipUseCase.findById(championshipId, mongoTemplate)
            .let { assertThat(it.get()).usingRecursiveComparison().isEqualTo(championshipDocument) }
    }

    @Test
    fun `given a invalid championship id - when database do not return a championship - Then method returns a empty optional`() {
        val championshipId = ObjectId().toString()
        Mockito.`when`(mongoTemplate.findById(eq(championshipId), eq(ChampionshipDocument::class.java)))
            .thenReturn(null)

        findChampionshipUseCase.findById(championshipId, mongoTemplate)
            .let { assertThat(it.isEmpty).isTrue }
    }
}
