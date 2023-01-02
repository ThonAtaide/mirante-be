package br.com.mirantebackend.services

import br.com.mirantebackend.dao.interfaces.MatchDao
import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.PageDto
import br.com.mirantebackend.exceptions.MatchNotFoundException
import br.com.mirantebackend.services.interfaces.MatchService
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class MatchServiceImpl(
    private val matchDao: MatchDao
) : MatchService {

    override fun createMatch(championshipId: String, matchDto: MatchDto): MatchDto {
        return matchDao.save(championshipId, matchDto)
    }

    override fun updateMatch(championshipId: String, matchId: String, matchDto: MatchDto): MatchDto {
        return matchDao.findById(championshipId, matchId)
            .map { matchDao.update(championshipId, matchId, matchDto) }
            .orElseThrow { MatchNotFoundException(matchId, championshipId) }
    }

    override fun findById(championshipId: String, matchId: String): MatchDto =
        matchDao.findById(championshipId, matchId)
            .orElseThrow { MatchNotFoundException(matchId, championshipId) }


    override fun findAll(
        championshipId: String?,
        championshipName: String?,
        principal: String?,
        challenger: String?,
        cup: String?,
        field: String?,
        playedAtStart: LocalDateTime?,
        playedAtEnd: LocalDateTime?,
        matchEnded: Boolean?,
        pageNumber: Int,
        pageSize: Int
    ): PageDto<MatchDto> {
        val matches = matchDao.findAllMatchesFromChampionship(
            championshipId = championshipId,
            championshipName = championshipName,
            pageNumber = pageNumber,
            pageSize = pageSize
        )

        return PageDto(matches.size.toLong(), pageNumber, matches)
    }


}