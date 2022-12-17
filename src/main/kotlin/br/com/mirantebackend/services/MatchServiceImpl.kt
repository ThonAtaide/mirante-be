package br.com.mirantebackend.services

import br.com.mirantebackend.controller.mappers.toMatchDocument
import br.com.mirantebackend.controller.mappers.toMatchDto
import br.com.mirantebackend.exceptions.MatchNotFoundException
import br.com.mirantebackend.repositories.MatchRepository
import br.com.mirantebackend.services.dto.matches.MatchDto
import br.com.mirantebackend.services.interfaces.MatchService
import org.springframework.stereotype.Service


@Service
class MatchServiceImpl(
    private val matchRepository: MatchRepository
) : MatchService {

    override fun createMatch(matchDto: MatchDto): MatchDto =
        matchDto.toMatchDocument().run {
            matchRepository.save(this)
        }.toMatchDto()


    override fun updateMatch(matchId: String, matchDto: MatchDto): MatchDto {
        return matchRepository.findById(matchId)
            .map {
                matchDto.id = matchId
                matchDto.toMatchDocument()
                    .let { matchRepository.save(it) }
                    .toMatchDto()
            }.orElseThrow{ MatchNotFoundException(matchId) }
    }

    override fun findById(matchId: String): MatchDto {
        return matchRepository.findById(matchId)
            .map { it.toMatchDto() }
            .orElseThrow { MatchNotFoundException(matchId) }
    }

    override fun findAll(): List<MatchDto> {
        return matchRepository.findAll()
            .map { it.toMatchDto() }
    }
}