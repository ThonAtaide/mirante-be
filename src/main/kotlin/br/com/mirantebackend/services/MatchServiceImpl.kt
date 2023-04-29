package br.com.mirantebackend.services

import br.com.mirantebackend.dao.interfaces.ChampionshipDao
import br.com.mirantebackend.dao.interfaces.MatchDao
import br.com.mirantebackend.exceptions.ChampionshipNotFoundException
import br.com.mirantebackend.exceptions.MatchCreationException
import br.com.mirantebackend.exceptions.MatchNotFoundException
import br.com.mirantebackend.exceptions.MatchUpdateException
import br.com.mirantebackend.mapper.toMatchDocument
import br.com.mirantebackend.mapper.toMatchDto
import br.com.mirantebackend.model.dto.matches.MatchDto
import br.com.mirantebackend.model.dto.pageable.PageDto
import br.com.mirantebackend.repository.ChampionshipDocumentRepository
import br.com.mirantebackend.repository.MatchDocumentRepository
import br.com.mirantebackend.services.interfaces.MatchService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MatchServiceImpl(
    private val matchDocumentRepository: MatchDocumentRepository,
    private val championshipDocumentRepository: ChampionshipDocumentRepository,
    private val matchDao: MatchDao
) : MatchService {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createMatch(championshipId: String, matchDto: MatchDto): MatchDto {
        logger.info { "Creating new match $matchDto on championship $championshipId" }
        try {
            return championshipDocumentRepository.findById(championshipId)
                .map { championship -> matchDocumentRepository.save(matchDto.toMatchDocument(championship)) }
                .orElseThrow { ChampionshipNotFoundException(championshipId) }
                .toMatchDto()
        } catch (err: ChampionshipNotFoundException) {
            logger.error { err.message }
            throw err
        } catch (err: Exception) {
            logger.error { err.message }
            throw MatchCreationException("An error occurred and maybe the match creation has failed.")
        }
    }

    override fun updateMatch(championshipId: String, matchId: String, matchDto: MatchDto): MatchDto {
        logger.info { "Updating match $matchDto on championship $championshipId" }
        try {
            return matchDocumentRepository.findByIdAndChampionship(matchId, championshipId)
                .map {
                    matchDocumentRepository.save(
                        matchDto.copy(id = matchId, createdAt = it.createdAt).toMatchDocument(it.championship)
                    )
                }
                .orElseThrow { MatchNotFoundException(matchId, championshipId) }
                .toMatchDto()
        } catch (err: MatchNotFoundException) {
            logger.error { err.message }
            throw err
        } catch (err: Exception) {
            logger.error { err.message }
            throw MatchUpdateException("An error occurred and maybe the match update has failed.")
        }
    }

    override fun findById(championshipId: String, matchId: String): MatchDto =
        matchDocumentRepository.findByIdAndChampionship(matchId, championshipId)
            .orElseThrow { MatchNotFoundException(matchId, championshipId) }
            .toMatchDto()

    override fun findAll(
        championshipId: String?,
        championshipName: String?,
        principal: String?,
        challenger: String?,
        field: String?,
        playedAtAfter: LocalDateTime?,
        playedAtBefore: LocalDateTime?,
        matchEnded: Boolean?,
        pageNumber: Int,
        pageSize: Int
    ): PageDto<MatchDto> = matchDao.findAll(
        championshipId = championshipId,
        championshipName = championshipName,
        principal = principal,
        challenger = challenger,
        field = field,
        matchEnded = matchEnded,
        pageNumber = pageNumber,
        pageSize = pageSize
    ).map { it.toMatchDto() }
        .let { PageDto(it.pageable.pageSize, it.pageable.pageNumber, it.totalElements, it.content) }
}
