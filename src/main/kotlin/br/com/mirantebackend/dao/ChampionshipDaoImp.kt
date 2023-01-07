package br.com.mirantebackend.dao

import br.com.mirantebackend.dao.interfaces.AbstractDao
import br.com.mirantebackend.dao.interfaces.ChampionshipDao
import br.com.mirantebackend.dao.usecases.interfaces.championship.CreateChampionshipUseCase
import br.com.mirantebackend.dao.usecases.interfaces.championship.FindChampionshipUseCase
import br.com.mirantebackend.dao.usecases.interfaces.championship.UpdateChampionshipUseCase
import br.com.mirantebackend.model.documents.ChampionshipDocument
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChampionshipDaoImp(
    private val createChampionshipUseCase: CreateChampionshipUseCase,
    private val updateChampionshipUseCase: UpdateChampionshipUseCase,
    private val findChampionshipUseCase: FindChampionshipUseCase,
    mongoTemplate: MongoTemplate
) : AbstractDao(mongoTemplate), ChampionshipDao {

    override fun save(championshipDocument: ChampionshipDocument): ChampionshipDocument =
        createChampionshipUseCase.createChampionship(championshipDocument, mongoTemplate)

    override fun updateNonNestedFields(
        championshipId: String,
        championshipDocument: ChampionshipDocument
    ): ChampionshipDocument =
        updateChampionshipUseCase.updateNonNestedFields(championshipId, championshipDocument, mongoTemplate)

    override fun findById(championshipId: String): Optional<ChampionshipDocument> =
        findChampionshipUseCase.findById(championshipId, mongoTemplate)

    override fun findAll(
        championshipName: String?,
        season: String?,
        organizedBy: String?,
        pageNumber: Int,
        pageSize: Int
    ): Page<ChampionshipDocument> =
        findChampionshipUseCase.findAll(championshipName, season, organizedBy, pageNumber, pageSize, mongoTemplate)
}