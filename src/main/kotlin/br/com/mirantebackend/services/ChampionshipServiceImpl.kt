package br.com.mirantebackend.services

import br.com.mirantebackend.dao.interfaces.ChampionshipDao
import br.com.mirantebackend.exceptions.ChampionshipCreationException
import br.com.mirantebackend.exceptions.ChampionshipNotFoundException
import br.com.mirantebackend.exceptions.ChampionshipUpdateException
import br.com.mirantebackend.mapper.toChampionshipDocument
import br.com.mirantebackend.mapper.toChampionshipDto
import br.com.mirantebackend.model.dto.championship.ChampionshipDto
import br.com.mirantebackend.model.dto.pageable.PageDto
import br.com.mirantebackend.repository.ChampionshipDocumentRepository
import br.com.mirantebackend.services.interfaces.ChampionshipService
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ChampionshipServiceImpl(
    private val championshipDocumentRepository: ChampionshipDocumentRepository,
    private val championshipDao: ChampionshipDao,
) : ChampionshipService {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createChampionship(championship: ChampionshipDto): ChampionshipDto {
        logger.info { "Creating championship $championship" }
        try {
            return championship.toChampionshipDocument()
                .let { championshipDocumentRepository.save(it) }
                .toChampionshipDto()
        } catch (err: Exception) {
            logger.error { err.message }
            throw ChampionshipCreationException("An error occurred and championship could not be created successfully.")
        }
    }

    override fun updateChampionship(championshipId: String, championship: ChampionshipDto): ChampionshipDto {
        logger.info { "Updating championship $championship with identifier $championshipId" }
        try {
            return championshipDocumentRepository.findById(championshipId)
                .map {
                    championshipDocumentRepository.save(
                        it.copy(
                            name = championship.name,
                            season = championship.season,
                            organizedBy = championship.organizedBy
                        )
                    )
                }
                .orElseThrow { ChampionshipNotFoundException(championshipId) }
                .toChampionshipDto()
        } catch (err: ChampionshipNotFoundException) {
            logger.error { err.message }
            throw err
        } catch (err: Exception) {
            logger.error { err.message }
            throw ChampionshipUpdateException("An error occurred and championship could not be updated successfully.")
        }
    }

    override fun getChampionship(championshipId: String): ChampionshipDto {
        logger.info { "Finding championship with identifier $championshipId" }
        try {
            return championshipDocumentRepository.findById(championshipId)
                .orElseThrow { ChampionshipNotFoundException(championshipId) }
                .toChampionshipDto()
        } catch (err: Exception) {
            logger.error { err.message }
            throw err
        }
    }

    override fun getChampionships(
        name: String?,
        pageNumber: Int,
        pageSize: Int,
    ): PageDto<ChampionshipDto> {
        logger.info { "Finding championships with the following parameters name: $name" }
        try {
            return championshipDao.findAll(championshipName = name, pageNumber = pageNumber, pageSize = pageSize)
                .map { it.toChampionshipDto() }
                .let { PageDto(it.pageable.pageSize, it.pageable.pageNumber, it.totalElements, it.content) }
        } catch (err: Exception) {
            logger.error { err.message }
            throw err
        }
    }
}
