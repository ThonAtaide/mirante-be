package br.com.mirantebackend.services

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

    override fun updateMatch(matchId: String, matchDto: MatchDto): MatchDto {
        logger.info { "Updating match $matchDto" }
        try {
            return matchDocumentRepository.findById(matchId)
                .map {
                    matchDocumentRepository.save(
                        matchDto.copy(id = matchId, createdAt = it.createdAt).toMatchDocument(it.championship)
                    )
                }
                .orElseThrow { MatchNotFoundException(matchId) }
                .toMatchDto()
        } catch (err: MatchNotFoundException) {
            logger.error { err.message }
            throw err
        } catch (err: Exception) {
            logger.error { err.message }
            throw MatchUpdateException("An error occurred and maybe the match update has failed.")
        }
    }

    override fun findById(matchId: String): MatchDto =
        matchDocumentRepository.findById(matchId)
            .orElseThrow { MatchNotFoundException(matchId) }
            .toMatchDto()

    override fun findAll(
        championshipId: String?,
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
        principal = principal,
        challenger = challenger,
        field = field,
        matchEnded = matchEnded,
        pageNumber = pageNumber,
        pageSize = pageSize
    ).map { it.toMatchDto() }
        .let { PageDto(it.pageable.pageSize, it.pageable.pageNumber, it.totalElements, it.content) }
}
