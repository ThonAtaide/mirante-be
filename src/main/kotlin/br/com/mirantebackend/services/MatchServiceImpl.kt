package br.com.mirantebackend.services

import br.com.mirantebackend.dao.interfaces.ChampionshipDao
import br.com.mirantebackend.dao.interfaces.MatchDao
import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.PageDto
import br.com.mirantebackend.exceptions.ChampionshipNotFoundException
import br.com.mirantebackend.exceptions.MatchCreationException
import br.com.mirantebackend.exceptions.MatchNotFoundException
import br.com.mirantebackend.exceptions.MatchUpdateException
import br.com.mirantebackend.services.interfaces.MatchService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class MatchServiceImpl(
    private val championshipDao: ChampionshipDao,
    private val matchDao: MatchDao
) : MatchService {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun createMatch(championshipId: String, matchDto: MatchDto): MatchDto {
        logger.info { "Creating new match $matchDto on championship $championshipId" }
        try {
            return championshipDao.findById(championshipId)
                .map { matchDao.save(championshipId, matchDto) }
                .orElseThrow { ChampionshipNotFoundException(championshipId) }
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
            return matchDao.findById(championshipId, matchId)
                .map { matchDao.update(championshipId, matchId, matchDto) }
                .orElseThrow { MatchNotFoundException(matchId, championshipId) }
        } catch (err: MatchNotFoundException) {
            logger.error { err.message }
            throw err
        } catch (err: Exception) {
            logger.error { err.message }
            throw MatchUpdateException("An error occurred and maybe the match update has failed.")
        }
    }

    override fun findById(championshipId: String, matchId: String): MatchDto =
        matchDao.findById(championshipId, matchId)
            .orElseThrow { MatchNotFoundException(matchId, championshipId) }

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
    )


}