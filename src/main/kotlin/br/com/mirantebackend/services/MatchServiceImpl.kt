package br.com.mirantebackend.services

import br.com.mirantebackend.controller.mappers.toMatchDto
import br.com.mirantebackend.controller.mappers.toTeamDocument
import br.com.mirantebackend.dao.MatchDaoImpl
import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.PageDto
import br.com.mirantebackend.exceptions.ChampionshipNotFoundException
import br.com.mirantebackend.exceptions.MatchNotFoundException
import br.com.mirantebackend.repositories.ChampionshipRepository
import br.com.mirantebackend.services.interfaces.MatchService
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class MatchServiceImpl(
    private val championshipRepository: ChampionshipRepository,
    private val matchDao: MatchDaoImpl
) : MatchService {

    override fun createMatch(championshipId: String, matchDto: MatchDto): MatchDto {

        return matchDao.save(championshipId, matchDto)
    }

    override fun updateMatch(championshipId: String, matchId: String, matchDto: MatchDto): MatchDto {
        return championshipRepository.findById(championshipId)
            .map { championshipDocument ->
                val matchDocument = championshipDocument.matches.stream()
                    .filter { match -> match.id.equals(matchId) }
                    .findFirst()
                    .orElseThrow { MatchNotFoundException(matchId, championshipId) }

                return@map matchDocument
                    .also {
                        it.field = matchDto.field
                        it.playedAt = matchDto.playedAt
                        it.principal = matchDto.principal.toTeamDocument()
                        it.challenger = matchDto.challenger.toTeamDocument()
                        it.matchEnded = matchDto.matchEnded
                    }.let { it ->
                        championshipDocument.matches.plus(it)
                        return@let championshipRepository.save(championshipDocument)
                            .matches.stream()
                            .filter { match -> match.id.equals(matchId) }
                            .findFirst()
                            .map { it.toMatchDto() }
                            .orElseThrow { MatchNotFoundException(matchId, championshipId) }
                    }
            }.orElseThrow { ChampionshipNotFoundException(championshipId) }
    }

    override fun findById(championshipId: String, matchId: String): MatchDto =
        matchDao.findById(championshipId, matchId)
            .orElseThrow { MatchNotFoundException(matchId, championshipId) }


    override fun findAll(
        championshipId: String,
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
//        val qChampionshipDocument = QChampionshipDocument.championshipDocument
//        val predicate = qChampionshipDocument.id.eq(championshipId)
//
//        val skip = (pageNumber) * pageSize
//        val resultSet = championshipRepository.findAll(
//            predicate,
//            PageRequest.of(pageNumber, pageSize)
//        ).map { it.matches }
//            .flatMap {
//                it.stream().skip(skip.toLong()).limit(pageSize.toLong()).map { match -> match.toMatchDto() }
//            }.sortedBy { it.createdAt }
//
//        val list = resultSet.toList()
//        return PageDto(list.size.toLong(), pageNumber, list)

        val query = Query().addCriteria(Criteria.where("_id").`is`(championshipId))
        query.fields().include("matches")
//        mongoTemplate.find(query, ChampionshipDocument::class.java)
        return PageDto(1, 2, emptyList())
    }


}